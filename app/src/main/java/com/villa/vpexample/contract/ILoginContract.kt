package com.villa.vpexample.contract

import com.exmple.corelib.http.entity.BaseBean
import com.exmple.corelib.http.entity.ListBean
import com.exmple.corelib.mvp.IPresenter
import com.exmple.corelib.mvp.IView
import com.villa.vpexample.data.ArticleData

/**
 * Description :
 * @author  villa_mou
 * @date 2018/11/20  15:58
 * 								 - generate by MvpAutoCodePlus plugin.
 */

interface ILoginContract {
    interface View : IView<Presenter> {
    }
    interface Presenter : IPresenter<View> {
        fun getData(succuss:(BaseBean<ListBean<ArticleData>>?)->Unit)
    }
}
