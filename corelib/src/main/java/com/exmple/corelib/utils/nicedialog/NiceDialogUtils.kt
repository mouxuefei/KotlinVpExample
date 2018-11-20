package com.exmple.corelib.utils.nicedialog

import android.support.v4.app.FragmentManager
import com.exmple.corelib.R


/**
 * Created by mou on 2017/9/19.
 */

object NiceDialogUtils  {

    fun showDialogAtBottom(resId: Int, fragmentManager: FragmentManager, viewConvertListener: ViewConvertListener) {
        NiceDialog
                .init()
                .setLayoutId(resId)
                .setConvertListener(viewConvertListener)
                .setAnimStyle(R.style.popwindow_bottom_style)
                .setDimAmount(0.5f)
                .setShowBottom(true)
                .setOutCancel(true)
                .show(fragmentManager)
    }

    fun showDialogAtCenter(resId: Int, fragmentManager: FragmentManager, viewConvertListener: ViewConvertListener) {
        NiceDialog.init()
                .setLayoutId(resId)
                .setConvertListener(viewConvertListener)
                .setAnimStyle(R.style.popwindow_center_style)
                .setDimAmount(0.5f)
                .setMargin(35)
                .setShowBottom(false)
                .setOutCancel(true)
                .show(fragmentManager)
    }

    fun showDialogAtCenterCanNotCancel(resId: Int, fragmentManager: FragmentManager, viewConvertListener: ViewConvertListener) {
        NiceDialog.init()
                .setLayoutId(resId)
                .setConvertListener(viewConvertListener)
                .setAnimStyle(R.style.popwindow_center_style)
                .setDimAmount(0.5f)
                .setMargin(35)
                .setShowBottom(false)
                .setOutCancel(false)
                .show(fragmentManager)
    }
}
