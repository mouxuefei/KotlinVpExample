package com.exmple.corelib.utils

import android.support.annotation.StringRes
import android.view.Gravity
import android.widget.Toast
import com.exmple.corelib.base.LibApplication
import org.jetbrains.annotations.NotNull

/**
 * @FileName: ToastUtils.java
 * @author: villa_mou
 * @date: 08-10:14
 * @version V1.0 <描述当前版本功能>
 * @desc
 */

fun showToastBottom(@NotNull msg: String) {
    val sToast = Toast.makeText(LibApplication.mContext, msg, Toast.LENGTH_SHORT)
    sToast.show()
}

fun showToastBottom(@StringRes msgResId: Int) {
    val sToast = Toast.makeText(LibApplication.mContext, LibApplication.mContext.resources.getString(msgResId), Toast.LENGTH_SHORT)
    sToast.show()
}

fun showToastCenter(@NotNull msg: String) {
    val sToast = Toast.makeText(LibApplication.mContext, msg, Toast.LENGTH_SHORT)
    sToast.setGravity(Gravity.CENTER, 0, 0)
    sToast.setText(msg)
    sToast.show()
}

fun showToastCenter(@StringRes msgResId: Int) {
    val sToast = Toast.makeText(LibApplication.mContext, LibApplication.mContext.resources.getString(msgResId), Toast.LENGTH_SHORT)
    sToast.setGravity(Gravity.CENTER, 0, 0)
    sToast.setText(LibApplication.mContext.resources.getString(msgResId))
    sToast.show()
    sToast.show()
}