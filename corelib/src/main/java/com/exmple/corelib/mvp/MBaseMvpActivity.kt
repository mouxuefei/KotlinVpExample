package com.exmple.corelib.mvp

import com.exmple.corelib.base.BaseActivity
import com.exmple.corelib.utils.showToastBottom

/**
 * @FileName: MBaseMvpActivity.java
 * @author: villa_mou
 * @date: 06-15:48
 * @version V1.0 <描述当前版本功能>
 * @desc
 */
abstract class MBaseMvpActivity<V : ITopView, P : ITopPresenter> : BaseActivity(), IView<P> {
    
    override fun bindView() {
        super.bindView()
        inited()
    }
    override fun getCtx() = this
    override fun showLoading(msg: String?) {
        mProgressDialog?.showProgressDialogWithText(msg)
    }

    override fun finish(resultCode: Int) {
        finish()
    }

    override fun showLoading(srtResId: Int) {
        mProgressDialog?.showProgressDialogWithText(resources.getString(srtResId))
    }

    override fun dismissLoading() {
        mProgressDialog?.dismissProgressDialog()
    }

    override fun showToast(message: String) {
        showToastBottom(message)
    }

    override fun showToast(srtResId: Int) {
        showToast(resources.getString(srtResId))
    }
}