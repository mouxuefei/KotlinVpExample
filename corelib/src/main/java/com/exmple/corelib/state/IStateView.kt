package com.exmple.corelib.state

import android.support.annotation.IntDef
import android.view.View

/**
 *
 * @author  XQ Yang
 * @date 5/6/2018  11:54 AM
 */

interface IStateView {
    var bindView: View?

    @ViewState
    var curState: Int
    var errorMsg:String
    var emptyMsg:String
    fun showSuccess()
    fun showError(msg: String? = null)
    fun showEmpty(msg: String? = null)
    fun showLoading()

    @ViewState
    var onStateChanged: ((Int) -> Unit)?

    var onRetry:(()->Unit)?

    fun <T, DATA : IListBean<T>> getState(bean: IStateBean<T, DATA>?): Int
    fun <T, DATA : IListBean<T>> setData(bean: IStateBean<T, DATA>?)
}


@IntDef(value = [STATE_SUCCESS, STATE_EMPTY, STATE_ERROR, STATE_LOADING], flag = false)
@Retention(AnnotationRetention.SOURCE)
annotation class ViewState


const val STATE_SUCCESS = 0
const val STATE_EMPTY = 1
const val STATE_ERROR = 2
const val STATE_LOADING=3

