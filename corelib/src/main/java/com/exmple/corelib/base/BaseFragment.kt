package com.exmple.corelib.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exmple.corelib.R
import com.exmple.corelib.utils.MLoadingView
import com.exmple.corelib.utils.register
import com.exmple.corelib.utils.unregister
import com.squareup.leakcanary.RefWatcher

/**
 * @FileName: com.mou.demo.basekotlin.BaseFragment.java
 * @author: mouxuefei
 * @date: 2017-12-19 15:48
 * @version V1.0 <描述当前版本功能>
 * @desc
 */
abstract class BaseFragment : Fragment() {
    private var isViewPrepare = false
    private var hasLoadData = false
    open var mContext: Context? = null
    private var mProgressDialog: MLoadingView? = null
    private var refWatcher: RefWatcher? = null
    protected abstract fun lazyLoad()
    open val useEventBus: Boolean = false
    @LayoutRes
    protected abstract fun getContentView(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = getContentView()
        val rootView = inflater.inflate(layout, container, false)
        this.mContext = context
        if (useEventBus) {
            register(this)
        }
        refWatcher = LibApplication.getRefWatcher(context)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView(view)
        mProgressDialog = MLoadingView(context!!, R.style.dialog_transparent_style)
        savedStanceState(savedInstanceState)
        initData()
        lazyLoadDataIfPrepared()
    }

    protected abstract fun initView(view: View)
    open fun savedStanceState(savedInstanceState: Bundle?) {}
    open fun initData() {}
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint&&isViewPrepare){
            loadDataVisible()
        }
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

     open fun loadDataVisible() {

    }

    fun showProgressDialog(text: String) {
        mProgressDialog?.showProgressDialogWithText(text)
    }

    fun dismissProgressDialog() {
        mProgressDialog?.dismissProgressDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus) {
            unregister(this)
        }
        refWatcher?.watch(this)
    }
}

inline fun <reified F : Fragment> Context.newFragment(vararg args: Pair<String, String>): F {
    val bundle = Bundle()
    args.let {
        for (arg in args) {
            bundle.putString(arg.first, arg.second)
        }
    }
    return Fragment.instantiate(this, F::class.java.name, bundle) as F
}