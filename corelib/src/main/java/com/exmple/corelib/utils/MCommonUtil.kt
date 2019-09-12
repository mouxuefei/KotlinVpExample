package com.exmple.corelib.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Environment
import com.exmple.corelib.base.LibApplication


/**
 * 是否有SD卡
 */
fun hasSdcard(): Boolean {
    val state = Environment.getExternalStorageState()
    return state == Environment.MEDIA_MOUNTED
}

/**
 * 查看网络是否连接
 */
fun isConnected(): Boolean {
    val info = getActiveNetworkInfo(LibApplication.mContext) ?: return false
    return info.isConnected
}

private fun getActiveNetworkInfo(context: Context): NetworkInfo? {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo
}
