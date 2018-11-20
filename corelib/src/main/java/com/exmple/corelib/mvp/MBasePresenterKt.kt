package com.exmple.corelib.mvp

import io.reactivex.disposables.CompositeDisposable

open class MBasePresenterKt<V : ITopView> : IPresenter<V> {
    override var mView: V? = null
    override val mDisposablePool: CompositeDisposable by lazy { CompositeDisposable() }

    override fun detachView() {
        super.detachView()
        if (!mDisposablePool.isDisposed) {
            mDisposablePool.clear()
        }
    }
}