package com.exmple.corelib.mvp

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import com.exmple.corelib.state.IStateView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.annotations.NotNull

/**
 * Description :
 * @author  XQ Yang
 * @date 2018/7/5  11:45
 */

interface ITopView : LifecycleOwner {
    fun getCtx(): Context?
    fun inited()
    fun finish(resultCode: Int = Activity.RESULT_CANCELED)
    fun showLoading( msg: String?)
    fun showLoading(@StringRes srtResId: Int)
    fun dismissLoading()
    fun showToast(@StringRes srtResId: Int)
    fun showToast(@NotNull message: String)
}

interface ITopPresenter : LifecycleObserver {
    fun attachView(view: ITopView)
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun detachView()
    val mDisposablePool: CompositeDisposable
}


interface IView<P : ITopPresenter> : ITopView {
    var mPresenter: P
    override fun inited() {
        mPresenter.attachView(this)
    }
}

interface IPresenter<V : ITopView> : ITopPresenter {
    var mView: V?
    fun getContext() = mView?.getCtx()
    override fun attachView(view: ITopView) {
        mView = view as V
        mView?.lifecycle?.addObserver(this)
    }

    override fun detachView() {
        mView = null
    }

    //判断是否初始化View
    private val isViewAttached: Boolean
        get() = mView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }
    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")
}

interface IListView<P : ITopPresenter> : IView<P> {
    val mRecyclerView: RecyclerView?
    val mStateView: IStateView?
    val mRefreshLayout: SmartRefreshLayout
    fun loadMoreFail()
    fun refreshEnd(success: Boolean)
}