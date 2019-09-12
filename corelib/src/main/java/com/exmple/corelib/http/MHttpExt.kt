package com.exmple.corelib.http

import com.exmple.corelib.http.constant.CodeStatus
import com.exmple.corelib.http.entity.BaseBean
import com.exmple.corelib.mvp.IListView
import com.exmple.corelib.mvp.ITopPresenter
import com.exmple.corelib.mvp.ITopView
import com.exmple.corelib.scheduler.SchedulerUtils
import com.exmple.corelib.utils.showToastBottom
import com.google.gson.JsonParseException
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun  <T> Observable<T>.mSubscribe(
        iBaseView: ITopView?= null
        , iTopPresenter: ITopPresenter? = null
        , msg: String = ""
        , onSuccess: (T) -> Unit) {
    this.compose(SchedulerUtils.ioToMain())
            .subscribe(object : Observer<T> {
                override fun onComplete() {
                    iBaseView?.dismissLoading()
                }

                override fun onSubscribe(d: Disposable) {
                    iTopPresenter?.mDisposablePool?.add(d)
                    iBaseView?.showLoading(if (msg.isEmpty()) "请求中..." else msg)

                }

                override fun onNext(t: T) {
                   val bean= t as BaseBean<Any>
                    if (bean.errorCode == CodeStatus.SUCCESS) {
                        onSuccess.invoke(t)
                    } else if (bean.errorCode == CodeStatus.LOGIN_OUT) {//重新登录
//                val currentActivity = MActivityUtils.currentActivity()
//                UserManager.getInstance().clear()
//                EMClient.getInstance().logout(true)
//                showToastBottom("登录过期，请重新登录")
//                val intent = Intent(currentActivity, LoginActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                currentActivity?.startActivity(intent)
                    } else {
                        if (!t.errorMsg.isEmpty()) {
                            t.errorMsg.let { showToastBottom(it) }
                        } else {
                            showToastBottom("请求失败")
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    Logger.e(e.toString())
                    iBaseView?.dismissLoading()
                    if (e is SocketTimeoutException || e is ConnectException||e is  UnknownHostException) {
                        showToastBottom("连接失败,请检查网络状况!")
                    } else if (e is JsonParseException) {
                        showToastBottom("数据解析失败")
                    } else {
                        showToastBottom("未知错误")
                    }
                }
            })
}

fun <T : BaseBean<Any>, P : ITopPresenter> Observable<T>.listSubcribe(
        iBaseView: IListView<P>? = null
        , iTopPresenter: ITopPresenter? = null
        , isRefresh: Boolean
        , isLoadMore: Boolean
        , onSuccess: (T) -> Unit) {
    this.compose(SchedulerUtils.ioToMain())
            .subscribe(object : Observer<T> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {
                    iTopPresenter?.mDisposablePool?.add(d)
                    if (!isRefresh && !isLoadMore) {
                        iBaseView?.mStateView?.showLoading()
                    }
                }

                override fun onNext(t: T) {
                    if (t.errorCode == CodeStatus.SUCCESS) {
                        iBaseView?.mStateView?.showSuccess()
                        iBaseView?.refreshEnd(success = true)
                        onSuccess.invoke(t)
                    } else if (t.errorCode == CodeStatus.LOGIN_OUT) {//重新登录
                        iBaseView?.refreshEnd(success = true)
//                    UserManager.getInstance().clear()
//                    showToastBottom("登录过期，请重新登录")
//                    EMClient.getInstance().logout(true)
//                    val intent = Intent(currentActivity, LoginActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    currentActivity.startActivity(intent)
                    } else {
                        iBaseView?.refreshEnd(success = false)
                        iBaseView?.mStateView?.showError()
                    }
                }

                override fun onError(e: Throwable) {
                    iBaseView?.refreshEnd(success = false)
                    if (!isLoadMore) {
                        iBaseView?.mStateView?.showError()
                    } else {
                        iBaseView?.loadMoreFail()
                    }
                }
            })
}

fun <T , P : ITopPresenter> Observable<T>.listNoBaseSubcribe(
        iBaseView: IListView<P>? = null
        , iTopPresenter: ITopPresenter? = null
        , isRefresh: Boolean
        , isLoadMore: Boolean
        , onSuccess: (T) -> Unit) {
    this.compose(SchedulerUtils.ioToMain())
            .subscribe(object : Observer<T> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {
                    iTopPresenter?.mDisposablePool?.add(d)
                    if (!isRefresh && !isLoadMore) {
                        iBaseView?.mStateView?.showLoading()
                    }
                }

                override fun onNext(t: T) {
//                    if (t.code == CodeStatus.SUCCESS) {
//                        iBaseView?.mStateView?.showSuccess()
//                        onSuccess.invoke(t)
//                    } else if (t.code == CodeStatus.LOGIN_OUT) {//重新登录
////                    UserManager.getInstance().clear()
////                    showToastBottom("登录过期，请重新登录")
////                    EMClient.getInstance().logout(true)
////                    val intent = Intent(currentActivity, LoginActivity::class.java)
////                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
////                    currentActivity.startActivity(intent)
//                    } else {
//                        iBaseView?.mStateView?.showError()
//                    }
                    iBaseView?.mStateView?.showSuccess()
                    iBaseView?.refreshEnd(success = true)
                    onSuccess.invoke(t)
                }

                override fun onError(e: Throwable) {
                    iBaseView?.refreshEnd(success = false)
                    if (!isLoadMore) {
                        iBaseView?.mStateView?.showError()
                    } else {
                        iBaseView?.loadMoreFail()
                    }
                }
            })
}