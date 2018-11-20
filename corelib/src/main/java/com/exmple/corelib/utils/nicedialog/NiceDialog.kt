package com.exmple.corelib.utils.nicedialog

import android.os.Bundle
import android.support.annotation.LayoutRes

class NiceDialog : BaseNiceDialog() {
    private var convertListener: ViewConvertListener? = null

    override fun intLayoutId(): Int {
        return layoutId
    }

    override fun convertView(holder: ViewHolder, dialog: BaseNiceDialog) {
        convertListener?.convertView(holder, dialog)
    }


    fun setLayoutId(@LayoutRes layoutId: Int): NiceDialog {
        this.layoutId = layoutId
        return this
    }

    fun setConvertListener(convertListener: ViewConvertListener): NiceDialog {
        this.convertListener = convertListener
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            convertListener = savedInstanceState.getParcelable("listener")
        }
    }

    /**
     * 保存接口
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("listener", convertListener)
    }

    companion object {
        fun init(): NiceDialog {
            return NiceDialog()
        }
    }
}
