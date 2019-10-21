package com.villa.vpexample.presenter

import com.exmple.corelib.http.entity.BaseBean
import com.exmple.corelib.http.entity.ListBean
import com.exmple.corelib.http.http
import com.exmple.corelib.mvp.MBasePresenterKt
import com.exmple.corelib.utils.showToastBottom
import com.villa.vpexample.contract.ILoginContract
import com.villa.vpexample.data.ArticleData
import com.villa.vpexample.http.MainRetrofit

/**
 * Description :
 * @author  villa_mou
 * @date 2018/11/20  15:58
 * 								 - generate by MvpAutoCodePlus plugin.
 */

class LoginPresenter : MBasePresenterKt<ILoginContract.View>(), ILoginContract.Presenter {
    override fun getData(succuss: (BaseBean<ListBean<ArticleData>>?) -> Unit) {
//        MainRetrofit.apiService.getArticle()
//                .bindDialogAndDisposable(mView, this)
//                .onResult {
//                    succuss.invoke(it)
//                }

        http<ListBean<ArticleData>> {
            api {
                MainRetrofit.apiService.getArticle()
            }
            loadingView(mView)
            disPool(this@LoginPresenter)
            onSuccess {
                showToastBottom("成功")
            }
            onError {

            }
        }
    }
}

