package com.exmple.corelib.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.exmple.corelib.R


/**
 *
 * @author mouxuefei
 * @date 2017/4/17
 * 加载对话框工具类
 */

class MLoadingView constructor(context: Context, style: Int) {
    private val mDialog: Dialog?
    private val mDialogContentView: View
    private val loadText: TextView
    init {
        mDialog = Dialog(context, style)
        mDialogContentView = LayoutInflater.from(context).inflate(R.layout.dialog_netwrok, null)
        loadText = mDialogContentView.findViewById<View>(R.id.tv_loading_text) as TextView
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.setContentView(mDialogContentView)
        val window = mDialog.window
        window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    companion object {
        private val TIME_DISMISS_DEFAULT: Long = 1500
    }
    /**
     * 显示加载的ProgressDialog
     */
    fun showProgressDialog() {
        if (mDialog != null && !mDialog.isShowing) {
            loadText.visibility = View.GONE
            mDialog.show()
        }
    }

    fun isShow():Boolean?{
        return mDialog?.isShowing
    }

    /**
     * 显示有加载文字ProgressDialog，文字显示在ProgressDialog的下面
     */
    fun showProgressDialogWithText(text: String) {
        if (TextUtils.isEmpty(text)) {
            showProgressDialog()
        } else {
            if (mDialog != null && !mDialog.isShowing) {

                loadText.text = text
                loadText.setTextColor(Color.WHITE)
                loadText.visibility = View.VISIBLE
                mDialog.show()
            }
        }
    }

    /**
     * 显示加载成功的ProgressDialog，文字显示在ProgressDialog的下面
     *
     * @param message 加载成功需要显示的文字
     * @param time    需要显示的时间长度(以毫秒为单位)
     */
    fun showProgressSuccess(message: String, time: Long = TIME_DISMISS_DEFAULT) {
        if (TextUtils.isEmpty(message)) {
            return
        }

        if (mDialog != null && !mDialog.isShowing) {
            loadText.text = message
            loadText.setTextColor(Color.WHITE)
            loadText.visibility = View.VISIBLE
            mDialog.show()
            mDialogContentView.postDelayed({ mDialog.dismiss() }, time)
        }
    }

    /**
     * 显示加载失败的ProgressDialog，文字显示在ProgressDialog的下面
     *
     * @param message 加载失败需要显示的文字
     * @param time    需要显示的时间长度(以毫秒为单位)
     */
    @JvmOverloads
    fun showProgressFail(message: String, time: Long = TIME_DISMISS_DEFAULT) {
        if (TextUtils.isEmpty(message)) {
            return
        }

        if (mDialog != null && !mDialog.isShowing) {
            loadText.text = message
            loadText.setTextColor(Color.WHITE)
            loadText.visibility = View.VISIBLE
            mDialog.show()
            mDialogContentView.postDelayed({ mDialog.dismiss() }, time)
        }
    }

    /**
     * 隐藏加载的ProgressDialog
     */
    fun dismissProgressDialog() {
        if (mDialog != null && mDialog.isShowing) {
            mDialog.dismiss()
        }
    }

}
