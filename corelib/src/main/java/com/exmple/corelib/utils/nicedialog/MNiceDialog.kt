package com.exmple.corelib.utils.nicedialog

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.*
import com.exmple.corelib.R
import com.exmple.corelib.utils.MUIUtils
import com.exmple.corelib.utils.MUIUtils.Companion.dp2px

inline fun mDialog(fragmentManager: FragmentManager, dsl: MNiceDialog.() -> Unit) {
    val dialog = MNiceDialog.init().apply(dsl)
    dialog.show(fragmentManager)
}


class MNiceDialog : DialogFragment() {
    var margin: Int = 0//左右边距
    var width: Int = 0//宽度
    var height: Int = 0//高度
    var dimAmount = 0.5f//灰度深浅
    var showBottom: Boolean = false//是否底部显示
    var outCancel = true//是否点击外部取消
    @StyleRes
    var animStyle: Int = 0
    @LayoutRes
    var layoutId: Int = 0

    var viewClick: DialogListner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.NiceDialog)
        layoutId = intLayoutId()
        //恢复保存的数据
        if (savedInstanceState != null) {
            margin = savedInstanceState.getInt(MARGIN)
            width = savedInstanceState.getInt(WIDTH)
            height = savedInstanceState.getInt(HEIGHT)
            dimAmount = savedInstanceState.getFloat(DIM)
            showBottom = savedInstanceState.getBoolean(BOTTOM)
            outCancel = savedInstanceState.getBoolean(CANCEL)
            animStyle = savedInstanceState.getInt(ANIM)
            layoutId = savedInstanceState.getInt(LAYOUT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutId, container, false)
        if (savedInstanceState != null) {
            viewClick = savedInstanceState.getParcelable("listener")
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        initParams()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewClick?.convertView(view, this)
    }

    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MARGIN, margin)
        outState.putInt(WIDTH, width)
        outState.putInt(HEIGHT, height)
        outState.putFloat(DIM, dimAmount)
        outState.putBoolean(BOTTOM, showBottom)
        outState.putBoolean(CANCEL, outCancel)
        outState.putInt(ANIM, animStyle)
        outState.putInt(LAYOUT, layoutId)
        outState.putParcelable("listener", viewClick)
    }

    private fun initParams() {
        val window = dialog.window
        if (window != null) {
            val lp = window.attributes
            //调节灰色背景透明度[0-1]，默认0.5f
            lp.dimAmount = dimAmount
            //是否在底部显示
            if (showBottom) {
                lp.gravity = Gravity.BOTTOM
                if (animStyle == 0) {
                    animStyle = R.style.popwindow_center_style
                }
            }

            //设置dialog宽度
            if (width == 0) {
                lp.width = (MUIUtils.screenWidth - 2 * dp2px(margin.toFloat())).toInt()
            } else {
                lp.width = MUIUtils.dp2px(width.toFloat()).toInt()
            }
            //设置dialog高度
            if (height == 0) {
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            } else {
                lp.height = MUIUtils.dp2px(height.toFloat()).toInt()
            }
            //设置dialog进入、退出的动画
            window.setWindowAnimations(animStyle)
            window.attributes = lp
        }
        isCancelable = outCancel
    }

    fun setMargin(margin: Int): MNiceDialog {
        this.margin = margin
        return this
    }

    fun setWidth(width: Int): MNiceDialog {
        this.width = width
        return this
    }

    fun setHeight(height: Int): MNiceDialog {
        this.height = height
        return this
    }

    fun setDimAmount(dimAmount: Float): MNiceDialog {
        this.dimAmount = dimAmount
        return this
    }

    fun setShowBottom(showBottom: Boolean): MNiceDialog {
        this.showBottom = showBottom
        return this
    }

    fun setOutCancel(outCancel: Boolean): MNiceDialog {
        this.outCancel = outCancel
        return this
    }

    fun setAnimStyle(@StyleRes animStyle: Int): MNiceDialog {
        this.animStyle = animStyle
        return this
    }

    fun show(manager: FragmentManager) {
        if (activity?.isFinishing == true) {
            return
        }
        if (layoutId == 0) {
            Log.e("MNiceDialog", "=============请设置布局==============")
            return
        }
        super.show(manager, System.currentTimeMillis().toString())
    }


    fun intLayoutId(): Int {
        return layoutId
    }

    fun setLayoutId(@LayoutRes layoutId: Int): MNiceDialog {
        this.layoutId = layoutId
        return this
    }

    companion object {
        private val MARGIN = "margin"
        private val WIDTH = "width"
        private val HEIGHT = "height"
        private val DIM = "dim_amount"
        private val BOTTOM = "show_bottom"
        private val CANCEL = "out_cancel"
        private val ANIM = "anim_style"
        private val LAYOUT = "layout_id"
        fun init(): MNiceDialog {
            return MNiceDialog()
        }
    }
}


 fun <V : View?> View.getView(id:Int): V {
    return  this.findViewById<V>(id)
}