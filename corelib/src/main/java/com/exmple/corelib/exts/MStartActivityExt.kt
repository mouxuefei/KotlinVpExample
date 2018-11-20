package com.exmple.corelib.exts

import android.app.Activity
import android.content.Intent

/**
 * @FileName: StartActivityExt.java
 * @author: villa_mou
 * @date: 08-10:27
 * @version V1.0 <描述当前版本功能>
 * @desc
 */

inline fun <reified A : Activity> Activity.myStartActivity(vararg args: Pair<String, String>, requestCode: Int = -1) {
    val intent = Intent(this, A::class.java)
    for (arg in args) {
        intent.putExtra(arg.first, arg.second)
    }
    if (requestCode > 0) {
        this.startActivityForResult(intent, requestCode)
    } else {
        this.startActivity(intent)
    }
}