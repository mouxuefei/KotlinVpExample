package com.exmple.corelib.mvp

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.exmple.corelib.R
import com.exmple.corelib.state.IStateView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.layout_list.*


/**
 * @FileName: {NAME}.java
 * @author: villa_mou
 * @date: {MONTH}-{HOUR}:01
 * @version V1.0 <描述当前版本功能>
 * @desc
 */
abstract class MBaseMvpListActivity<V : ITopView, P : ITopPresenter> : MBaseMvpActivity<V, P>(), IListView<P> {

    override val mStateView: IStateView by lazy { stateview }
    override val mRecyclerView: RecyclerView by lazy { recyclerview }
    override val mRefreshLayout: SmartRefreshLayout by lazy { refreshLayout }
    open val setRecyclerViewBgColor = R.color.white
    open val setRefreshEnable = true
    open val autoRefresh = true
    override fun getContentView() = R.layout.layout_list
    override fun initView() {
        //设置列表背景色
        recyclerview.setBackgroundColor(ContextCompat.getColor(this, setRecyclerViewBgColor))
        //重试
        stateview.onRetry = { onRetry() }
        //刷新
        refreshLayout.setOnRefreshListener { onRefresh() }
        //设置下拉刷新是否可用
        refreshLayout.isEnabled = setRefreshEnable
        if (setRefreshEnable && autoRefresh) {
            //自动刷新
            refreshLayout.autoRefresh()
        }
    }

    override fun refreshEnd(success: Boolean) {
        refreshLayout.finishRefresh(success)
    }

    abstract fun onRefresh()
    abstract fun onRetry()
}