package com.exmple.corelib.utils

/**
 * Created by moses on 2016/11/10.
 */

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

//打开或关闭软键盘
object MKeyBoardUtils {
    /**
     * 打开软键盘
     * 魅族可能会有问题
     *
     * @param mEditText
     * @param mContext
     */
    fun openKeybord(mEditText: EditText, mContext: Context) {
        val imm = mContext
                .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     * @param mContext
     */
    fun closeKeybord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }


    /**
     * des:隐藏软键盘,这种方式参数为activity
     *
     * @param activity
     */
    fun hideInputForce(activity: Activity?) {
        if (activity == null || activity.currentFocus == null) {
            return
        }

        (activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(activity.currentFocus!!
                        .windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 打开键盘
     */
    fun showInput(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null) {
            view.requestFocus()
            imm.showSoftInput(view, 0)
        }
    }


}