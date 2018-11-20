package com.exmple.corelib.exts

import java.lang.ref.WeakReference
import kotlin.reflect.KProperty


/**
 * 第一种方式实现弱引用,但是T.weakReference()
 */
fun <T : Any> T.weakReference() = WeakReference(this)

/**
 * 第二种实现采用委托属性弱引用,val T by Weak{ T }
 */
class Weak<T : Any>(initializer: () -> T?) {
    private var weakReference = WeakReference(initializer())
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return weakReference.get()
    }
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        weakReference = WeakReference(value)
    }
}

