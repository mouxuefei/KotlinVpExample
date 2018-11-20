package com.exmple.corelib.exts

import android.support.annotation.DrawableRes
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exmple.corelib.base.LibApplication.Companion.mContext
import com.exmple.corelib.utils.MGlideRoundedCornersTransform


/**
 * 加载普通图片给imageview
 */
fun ImageView.loadUrl(imgUrl: String, @DrawableRes placeholder:Int, @DrawableRes error:Int) {
    if (TextUtils.isEmpty(imgUrl)) {
        return
    }
    val options = RequestOptions()
    options.placeholder(placeholder)
    options.error(error)
    Glide.with(context).load(imgUrl).apply(options).into(this)
}

/**
 * 加载圆角图片给imageview
 */
fun ImageView.loadUrlRoundCorner(imgUrl: String, cornerDp: Float,@DrawableRes placeholder:Int,@DrawableRes error:Int) {
    if (TextUtils.isEmpty(imgUrl)) {
        return
    }
    val options = RequestOptions()
    options.placeholder(placeholder)
    options.error(error)
    options.optionalTransform(MGlideRoundedCornersTransform(cornerDp, MGlideRoundedCornersTransform.CornerType.ALL))
    Glide.with(mContext).load(imgUrl).apply(options).into(this)
}

class GlideWrapper {

    var url: String? = null

    var imageView: ImageView? = null

    @DrawableRes
    var placeholder: Int = -1

    @DrawableRes
    var error: Int = -1

    var corner: Float = -1f
}

fun loadImage(init: GlideWrapper.() -> Unit) {
    val glideWrapper = GlideWrapper()
    glideWrapper.init()
    executeWrapper(glideWrapper)
}

fun executeWrapper(glideWrapper: GlideWrapper) {
    glideWrapper.imageView?.let {
        val options = RequestOptions()
        if (glideWrapper.placeholder > 0) {
            options.placeholder(glideWrapper.placeholder)
        }
        if (glideWrapper.error>0){
            options.error(glideWrapper.error)
        }
        if (glideWrapper.corner>0){
            options.optionalTransform(MGlideRoundedCornersTransform(glideWrapper.corner, MGlideRoundedCornersTransform.CornerType.ALL))
        }
        Glide.with(mContext).load(glideWrapper.url).apply(options).into(it)
    }
}
