/*
 * Copyright (c) 18-1-8 下午1:38. XQ Yang
 */

package com.exmple.corelib.http

import android.view.View
import com.exmple.corelib.http.entity.BaseBean
import com.exmple.corelib.mvp.ITopPresenter
import com.exmple.corelib.mvp.ITopView
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import java.lang.ref.WeakReference


//@Deprecated("用扩展dsl")
//fun <T : IStateBean> Observable<T>.mySubscribe(loadingView: ILoadingView? = null, msg: String = "", onError: ((Throwable) -> Unit)? = null, onSuccess: (T) -> Unit) {
//    this.subscribe(object : Observer<T> {
//        override fun onComplete() {
//            loadingView?.dismissLoading()
//        }
//
//        override fun onSubscribe(d: Disposable) {
//            loadingView?.showLoading(if (msg.isEmpty()) "读取中..." else "${msg}中...")
//            if (!NetworkUtils.isConnected) {
//                ToastUtils.showShort("当前无网络")
//                onComplete()
//            }
//        }
//
//        override fun onNext(t: T) {
//            if (t.isOk()) {
//                onSuccess.invoke(t)
//            } else {
//                if (!TextUtils.isEmpty(t.message)) {
//                    onError(ResultException(t.state, t.message))
//                } else {
//                    onError(ResultException(t.state, msg + "失败,请检查网络或稍后重试"))
//                }
//            }
//        }
//
//        override fun onError(e: Throwable) {
//            loadingView?.dismissLoading()
//            if (e is ResultException) {
//                ToastUtils.showShort(e.message ?: "")
//            }
//            onError?.invoke(e)
//        }
//    })
//}

open class BaseSubscribeWrap<R> {
    internal var disPool: ITopPresenter? = null
    internal lateinit var observable: Observable<BaseBean<R>>
    internal var loadingView: ITopView? = null
    internal var needLockView: WeakReference<View>? = null
    internal var success: ((result: R) -> Unit) = {}
    internal var error: ((e: Throwable) -> Unit) = {}
    internal var loadingMsg: String? = null
    internal var doOnIo: Boolean = false

    fun disPool(disPool: ITopPresenter) {
        this.disPool = disPool
    }

    /**
     * 在子线程回调
     */
    fun doOnIo() {
        this.doOnIo = true
    }

    /**
     *
     */
    fun loadingView(loadingView: ITopView?, loadingMsg: String? = null) {
        this.loadingView = loadingView
        this.loadingMsg = loadingMsg
    }

//    fun stateListener(stateListener: MutableLiveData<RequestState>?) {
//        this.stateListener = stateListener
//    }

    /**
     * 防止连点 该view只有在返回后才能再点击.
     */
    fun lockView(view: View) {
        needLockView = WeakReference(view)
    }

    fun api(service: () -> Observable<BaseBean<R>>) {
        this.observable = service.invoke()
    }

    fun onSuccess(success: ((result: R) -> Unit)) {
        this.success = success
    }

    fun onError(error: ((e: Throwable) -> Unit)) {
        this.error = error
    }
}

//class BaseSubscribeUniWrap<R, S>: BaseSubscribeWrap<R, S>(){
//    internal lateinit var apiSelectUniversal: (service: S, parameter: RequestMap) -> Observable<R>
//    fun apiUni(api: (service: S, parameter: RequestMap) -> Observable<R>) {
//        this.apiSelectUniversal = api
//    }
//}



fun <RESULT> http(init: BaseSubscribeWrap<RESULT>.() -> Unit) {
    val wrap = BaseSubscribeWrap<RESULT>()
    wrap.init()
    if (wrap.disPool == null) {
        Logger.e("disPool is null, Memory leaks are possible!!!")
    }
    if (wrap.doOnIo) {
        wrap.observable= wrap.observable.asyncIo()
    } else {
        wrap.observable= wrap.observable.async()
    }
    wrap.loadingView?.let {
        wrap.observable=wrap.observable.bindDialog(it,wrap.loadingMsg)
    }
    wrap.observable.doOnSubscribe {
        wrap.needLockView?.get()?.isEnabled = false
    }.doOnComplete {
        wrap.needLockView?.get()?.isEnabled = true
    }
    wrap.disPool?.let {
        wrap.observable= wrap.observable.bindDisposable(it)
    }
    wrap.observable.onResult({
        wrap.error.invoke(it)
    }, {
        wrap.success.invoke(it.data)
    })

}
/**
 *  这个是传啥，返回啥，没有包裹一个公共属性
 */
//@SuppressLint("CheckResult")
//fun <RESULT> httpUni(init: BaseSubscribeUniWrap<RESULT, ProjectCoreHttpService>.() -> Unit) {
//    val wrap = BaseSubscribeUniWrap<RESULT, ProjectCoreHttpService>()
//    wrap.init()
//    val subUniversual = object : AbstractSubscribeUniversal<RESULT, ProjectCoreHttpService>(wrap.disPool, wrap.parameter) {
//        override fun getObservable(service: ProjectCoreHttpService, factory: RequestMap): Observable<RESULT> {
//            return wrap.apiSelectUniversal.invoke(service, factory)
//        }
//    }
//    if (wrap.doOnIo) {
//        ProjectCoreHttpManager.dealOnIO(subUniversual)
//    } else {
//        ProjectCoreHttpManager.deal(subUniversual)
//    }.subscribe(object : BaseObserver<RESULT>(wrap.loadingView, msg = wrap.loadingMsg) {
//        override fun onStart() {
//            wrap.needLockView?.get()?.isEnabled = false
//            super.onStart()
//            wrap.stateListener?.postValue(RequestState.LOADING)
//        }
//
//        override fun onNext(t: RESULT) {
//            wrap.success.invoke(t)
//        }
//
//        override fun onComplete() {
//            super.onComplete()
////            wrap.stateListener?.postValue(RequestState.LOADED)
//            wrap.needLockView?.get()?.isEnabled = true
//            wrap.needLockView?.clear()
//            wrap.needLockView = null
//        }
//
//        override fun onError(e: Throwable) {
//            super.onError(e)
//            wrap.error.invoke(e)
//            wrap.stateListener?.postValue(RequestState.error(e.message))
//            wrap.needLockView?.get()?.isEnabled = true
//            wrap.needLockView?.clear()
//            wrap.needLockView = null
//        }
//    })
//}


//var mDialog: WeakReference<MLoadingView>? = null
//
//fun dismiss() {
//    mDialog?.get()?.dismissProgressDialog()
//    mDialog?.clear()
//    mDialog = null
//}
//
//@Deprecated("用扩展dsl")
//fun <T : IStateBean> Observable<T>.dSubscribe(context: Context, msg: String = "", autoDismiss: Boolean = true, onError: ((Throwable) -> Unit)? = null, onSuccess: (T) -> Unit) {
//    this.subscribe(object : Observer<T> {
//        override fun onComplete() {
//            if (autoDismiss) {
//                dismiss()
//            }
//        }
//
//        override fun onSubscribe(d: Disposable) {
//            if (!NetworkUtils.isConnected) {
//                ToastUtils.showShort("当前无网络")
//                onComplete()
//            } else {
//                if (mDialog == null || mDialog?.get() == null) {
//                    mDialog = WeakReference(MLoadingView(context, R.style.dialog_transparent_style))
//                }
//                mDialog?.get()!!.show(if (msg.isNotEmpty()) "${msg}中..." else "", true, autoDismiss)
//            }
//        }
//
//        override fun onNext(t: T) {
//            if (t.isOk()) {
//                onSuccess.invoke(t)
//            } else {
//                if (!TextUtils.isEmpty(t.message)) {
//                    onError(ResultException(t.state, t.message))
//                } else {
//                    onError(ResultException(t.state, msg + "失败,请检查网络或稍后重试"))
//                }
//            }
//            if (autoDismiss) {
//                dismiss()
//            }
//        }
//
//        override fun onError(e: Throwable) {
//            if (e is ResultException) {
//                ToastUtils.showShort(e.message)
//            }
//            onError?.invoke(e)
//            dismiss()
//        }
//    })
//}
