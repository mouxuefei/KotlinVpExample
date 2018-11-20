package com.exmple.corelib.exts

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.exmple.corelib.R
import com.exmple.corelib.base.LibApplication
import com.exmple.corelib.utils.MUIUtils.Companion.dp2px

/**
 * @FileName: ToastExt.java
 * @author: villa_mou
 * @date: 08-14:27
 * @version V1.0 <描述当前版本功能>
 * @desc
 */

fun Toast.setGravityCenter(): Toast {
    setGravity(Gravity.CENTER, 0, 0)
    return this
}

/**
 * 设置Toast字体及背景颜色
 * @param messageColor
 * @param backgroundColor
 * @return
 */
fun Toast.setToastColor(@ColorInt messageColor: Int, @ColorInt backgroundColor: Int) {
    val view = view
    if (view != null) {
        val message = view.findViewById(android.R.id.message) as TextView
        message.setBackgroundColor(backgroundColor)
        message.setTextColor(messageColor)
    }
}

/**
 * 设置Toast字体及背景
 * @param messageColor
 * @param background
 * @return
 */
fun Toast.setBackground(@ColorInt messageColor: Int = Color.WHITE, @DrawableRes background: Int = R.drawable.shape_toast_bg): Toast {
    val view = view
    if (view != null) {
        val message = view.findViewById(android.R.id.message) as TextView
        view.setBackgroundResource(background)
        message.setTextColor(messageColor)
    }
    return this
}

//@SuppressLint("ShowToast")
fun toast(text: CharSequence): Toast = Toast.makeText(LibApplication.mContext, text, Toast.LENGTH_LONG)
        .setGravityCenter()
        .setBackground()
//需要的地方调用withErrorIcon，默认不要添加
//        .withErrorIcon()


//@SuppressLint("ShowToast")
fun toast(@StringRes res: Int): Toast = Toast.makeText(LibApplication.mContext, LibApplication.mContext.resources.getString(res), Toast.LENGTH_LONG)
        .setGravityCenter()
        .setBackground()
//需要的地方调用withErrorIcon，默认不要添加
//        .withErrorIcon()

fun Toast.withErrorIcon(@DrawableRes iconRes: Int = R.drawable.error): Toast {
    val view = view
    if (view != null) {
        val layout = this.view as LinearLayout
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val icon = ImageView(LibApplication.mContext)
        icon.setImageResource(iconRes)
        icon.setPadding(0, 0, dp2px(8f).toInt(), 0)
        icon.layoutParams = layoutParams
        layout.orientation = LinearLayout.HORIZONTAL
        layout.gravity = Gravity.CENTER_VERTICAL
        layout.addView(icon, 0)
    }
    return this
}

fun Toast.withSuccIcon(@DrawableRes iconRes: Int = R.drawable.success): Toast {
    val view = view
    if (view != null) {
        val layout = this.view as LinearLayout
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val icon = ImageView(LibApplication.mContext)
        icon.setImageResource(iconRes)
        icon.setPadding(0, 0, dp2px(8f).toInt(), 0)
        icon.layoutParams = layoutParams
        layout.orientation = LinearLayout.HORIZONTAL
        layout.gravity = Gravity.CENTER_VERTICAL
        layout.addView(icon, 0)
    }
    return this
}

class ToastWrapper {

    var text: String? = null

    var res: Int? = null

    var showSuccess: Boolean = false

    var showError: Boolean = false
}

fun toast(init: ToastWrapper.() -> Unit) {
    val wrap = ToastWrapper()
    wrap.init()
    execute(wrap)
}

private fun execute(wrap: ToastWrapper) {

    var taost: Toast? = null

    wrap.text?.let {
        taost = toast(it)
    }
    wrap.res?.let {
        taost = toast(it)
    }

    if (wrap.showSuccess) {

        taost?.withSuccIcon()
    } else if (wrap.showError) {

        taost?.withErrorIcon()
    }

    taost?.show()
}
/**
 * 如何使用
 */
//toast {
//
//    res = R.string.you_have_not_completed_the_email_address
//    showError = true
//}