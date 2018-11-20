package com.exmple.corelib.exts

import android.widget.TextView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * 它满足两个条件：
 *使用operator进行修饰
 *使用了plusAssign作为函数名
 *所以可以重载复合运算符+=
 */
operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

fun TextView.stringTrim(): String {
    return this.text.toString().trim()
}