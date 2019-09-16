package com.villa.vpexample.view

import com.exmple.corelib.mvp.MBaseMvpActivity
import com.villa.vpexample.R
import com.villa.vpexample.contract.ILoginContract
import com.villa.vpexample.presenter.LoginPresenter

/**
 * Description :
 * @author  villa_mou
 * @date 2018/11/20  15:58
 * 								 - generate by MvpAutoCodePlus plugin.
 */

class LoginActivity : MBaseMvpActivity<ILoginContract.View, ILoginContract.Presenter>(), ILoginContract.View {

    override var mPresenter: ILoginContract.Presenter = LoginPresenter()
    override fun getContentView() = R.layout.main_activity
    override fun initView() {
        mPresenter.getData{

        }
    }
}

