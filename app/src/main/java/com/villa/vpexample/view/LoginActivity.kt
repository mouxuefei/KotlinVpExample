package com.villa.vpexample.view

import android.view.View
import android.widget.Button
import com.exmple.corelib.mvp.MBaseMvpActivity
import com.exmple.corelib.utils.nicedialog.DialogListner
import com.exmple.corelib.utils.nicedialog.MNiceDialog
import com.exmple.corelib.utils.nicedialog.getView
import com.exmple.corelib.utils.nicedialog.mDialog
import com.villa.vpexample.R
import com.villa.vpexample.contract.ILoginContract
import com.villa.vpexample.presenter.LoginPresenter
import kotlinx.android.synthetic.main.main_activity.*

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
        btn.setOnClickListener {
            //            MNiceDialog
//                    .init()
//                    .setLayoutId(R.layout.dialog_demo)
//                    .show(supportFragmentManager)
//                    .setConvertListener(object : ViewConvertListener() {
//                        override fun convertView(holder: ViewHolder, dialog: MNiceDialog) {
//                            holder.getView<Button>(R.id.button)?.setOnClickListener {
//                                showToast("button")
//                            }
//                        }
//                    })

            mDialog(supportFragmentManager) {
                layoutId=R.layout.dialog_demo
                margin = 30
                outCancel = true
                viewClick = object : DialogListner() {
                    override fun convertView(view: View,dialog: MNiceDialog) {
                        view.getView<Button>(R.id.button).setOnClickListener {
                            showToast("button")
                            dialog.dismiss()
                        }
                    }
                }
            }

        }
    }
}

