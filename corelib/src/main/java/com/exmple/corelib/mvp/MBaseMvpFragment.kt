package com.exmple.corelib.mvp

import android.os.Bundle
import android.view.View
import com.exmple.corelib.base.BaseFragment
import com.exmple.corelib.utils.showToastBottom


/**
 * @FileName: MBaseMvpFragment.java
 * @author: villa_mou
 * @date: 06-16:20
 * @version V1.0 <描述当前版本功能>
 * @desc
 */
abstract class MBaseMvpFragment<V : ITopView, P : ITopPresenter> : BaseFragment(), IView<P> {
    override fun getCtx() = context
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inited()
    }

    override fun finish(resultCode: Int) {
    }
    override fun showToast(message: String) {
        showToastBottom(message)
    }
    override fun showToast(srtResId: Int) {
        showToast(resources.getString(srtResId))
    }
    override fun showLoading(msg: String?) {
        showProgressDialog(msg)
    }

    override fun showLoading(srtResId: Int) {
        showProgressDialog(resources.getString(srtResId))
    }

    override fun dismissLoading() {
        dismissProgressDialog()
    }
}