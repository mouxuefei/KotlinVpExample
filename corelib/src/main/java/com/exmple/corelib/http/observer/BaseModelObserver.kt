package com.exmple.corelib.http.observer

import com.exmple.corelib.http.constant.CodeStatus
import com.exmple.corelib.http.entity.BaseBean
import com.exmple.corelib.utils.showToastBottom
import com.google.gson.JsonParseException
import com.orhanobut.logger.Logger
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * @FileName: com.mou.demo.http.observer.BaseObserver.java
 * @author: mouxuefei
 * @date: 2017-12-21 16:40
 * @version V1.0 配置了baseModel,状态码统一处理的observer
 * @desc
 */
abstract class BaseModelObserver<T: BaseBean> : Observer<T> {
    open  val onBegin: (() -> Unit)?=null
    open  val failError: (() -> Unit)?=null
    open  val onHandleSuccess: ((t: T?) -> Unit)?=null
    override fun onSubscribe(d: Disposable) {
        onBegin?.invoke()
    }

    override fun onNext(value: T) {
        if (value.code == CodeStatus.SUCCESS) {
            onHandleSuccess?.invoke(value)
        } else {
            onHandleError(value.msg)
        }
    }

    override fun onError(e: Throwable) {
        showErrorToast(e)
        failError?.invoke()
    }

    override fun onComplete() {}

    open fun codeError(code: Int) {

    }

    private fun onHandleError(msg: String) {
        if (!msg.isEmpty()) {
            showToastBottom(msg)
        } else {
            showToastBottom("未知错误")
        }
    }


    private fun showErrorToast(e: Throwable) {
        Logger.e("exception=${e.toString()}")
        if (e is SocketTimeoutException || e is ConnectException) {
            showToastBottom("连接失败,请检查网络状况!")
        } else if (e is JsonParseException) {
            showToastBottom("数据解析失败")
        } else {
            showToastBottom("请求失败")
        }
    }
}