package com.exmple.corelib.exts

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.view.View

/**
 * @FileName: UIExt.java
 * @author: villa_mou
 * @date: 08-10:58
 * @version V1.0 <描述当前版本功能>
 * @desc
 */

/**
 * dp转px
 */
 fun View.dp2px(dipValue: Float): Float {
    return (dipValue * this.resources.displayMetrics.density + 0.5f)
}

/**
 * px转dp
 */
fun View.px2dp(pxValue: Float): Float {
    return (pxValue / this.resources.displayMetrics.density + 0.5f)
}

/**
 * sp转px
 */
fun View.sp2px(spValue: Float): Float {
    return (spValue * this.resources.displayMetrics.scaledDensity + 0.5f)
}

/**
 * 获取res颜色
 */
fun Context.getColor(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

/**
 * 获取res的drawable
 */
fun Context.getDrawable(@DrawableRes drawableId: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableId)
}