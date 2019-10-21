//package com.exmple.corelib.utils.dialog
//
//import android.os.Bundle
//import android.support.v4.app.FragmentManager
//import android.support.v7.widget.AppCompatTextView
//import android.view.Gravity
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.exmple.corelib.R
//
///**
// * @FileName: MDialog.java
// * @author: villa_mou
// * @date: 10-14:03
// * @version V1.0 <描述当前版本功能>
// * @desc
// */
//
////  DSL style
//inline fun mDialog(fragmentManager: FragmentManager, dsl: AskDialog.() -> Unit) {
//    val dialog = AskDialog.newInstance().apply(dsl)
//    dialog.show(fragmentManager, "dialog")
//}
//
//open class AskDialog : BaseFragmentDialog() {
//
//    var mTitle: String? = null
//    var mMessage: String? = null
//    var msgGravity: Int = Gravity.CENTER
//    var onlySure: Boolean = false
//    var mColor: Int = 0
//    var mCancelText: String = ""
//    var mSureText: String = ""
//
//    protected var cancelClicks: (() -> Unit)? = null
//    protected var sureClicks: (() -> Unit)? = null
//
//    override fun setView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        val view = inflater.inflate(R.layout.layout_ask_dialog, container, false)
//        val dialogTitle = view.findViewById<AppCompatTextView>(R.id.title)
//        val dialogMessage = view.findViewById<AppCompatTextView>(R.id.message)
//        val sureButton = view.findViewById<AppCompatTextView>(R.id.sure)
//        val cancelButton = view.findViewById<AppCompatTextView>(R.id.cancel)
//        val line = view.findViewById<View>(R.id.vertical_line)
//        cancelButton.isVisibility(!onlySure)
//        line.isVisibility(!onlySure)
//
//        dialogTitle.text = mTitle
//        mMessage?.also {
//            dialogMessage.visibility = View.VISIBLE
//            dialogMessage.text = it
//            dialogMessage.gravity = msgGravity
//        }
//        cancelButton.text = mCancelText
//        cancelButton.setTextColor(mColor)
//        cancelButton.setOnClickListener {
//            cancelClicks?.let { onClick ->
//                onClick()
//            }
//            dismiss()
//        }
//
//        sureButton.text = mSureText
//        sureButton.setTextColor(mColor)
//        sureButton.setOnClickListener {
//            sureClicks?.let { onClick ->
//                onClick()
//            }
//            dismiss()
//        }
//
//        return view
//    }
//
//    fun cancelClick(key: String? = null, onClick: () -> Unit) {
//        key?.let {
//            mCancelText = it
//        }
//        cancelClicks = onClick
//    }
//
//    fun sureClick(key: String? = null, onClick: () -> Unit) {
//        key?.let {
//            mSureText = it
//        }
//        sureClicks = onClick
//    }
//
//    companion object {
//        fun newInstance(): AskDialog = AskDialog()
//    }
//
//}