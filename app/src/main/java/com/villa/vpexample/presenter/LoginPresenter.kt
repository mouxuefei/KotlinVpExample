package com.villa.vpexample.presenter

import com.exmple.baseprojectmvp.http.MainRetrofit
import com.exmple.corelib.http.mSubscribe
import com.exmple.corelib.mvp.MBasePresenterKt
import com.exmple.corelib.utils.showToastBottom
import com.villa.vpexample.contract.ILoginContract
import com.villa.vpexample.util.DeviceUtils

/**
 * Description :
 * @author  villa_mou
 * @date 2018/11/20  15:58
 * 								 - generate by MvpAutoCodePlus plugin.
 */

class LoginPresenter : MBasePresenterKt<ILoginContract.View>(), ILoginContract.Presenter {
    override fun getData() {
        MainRetrofit.apiService.loginApp(DeviceUtils.getUUID()).mSubscribe(mView, this) {
            showToastBottom("成功")
        }
    }
}

