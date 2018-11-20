package com.exmple.corelib.utils.nicedialog

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.*
import com.exmple.corelib.R
import com.exmple.corelib.utils.MUIUtils
import com.exmple.corelib.utils.MUIUtils.Companion.dp2px


abstract class BaseNiceDialog : DialogFragment() {
    private var margin: Int = 0//左右边距
    private var width: Int = 0//宽度
    private var height: Int = 0//高度
    private var dimAmount = 0.5f//灰度深浅
    private var showBottom: Boolean = false//是否底部显示
    private var outCancel = true//是否点击外部取消
    @StyleRes
    private var animStyle: Int = 0
    @LayoutRes
    protected var layoutId: Int = 0

    abstract fun intLayoutId(): Int

    abstract fun convertView(holder: ViewHolder, dialog: BaseNiceDialog)

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
        convertView(ViewHolder.create(view), this)
        return view
    }

    override fun onStart() {
        super.onStart()
        initParams()
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

    fun setMargin(margin: Int): BaseNiceDialog {
        this.margin = margin
        return this
    }

    fun setWidth(width: Int): BaseNiceDialog {
        this.width = width
        return this
    }

    fun setHeight(height: Int): BaseNiceDialog {
        this.height = height
        return this
    }

    fun setDimAmount(dimAmount: Float): BaseNiceDialog {
        this.dimAmount = dimAmount
        return this
    }

    fun setShowBottom(showBottom: Boolean): BaseNiceDialog {
        this.showBottom = showBottom
        return this
    }

    fun setOutCancel(outCancel: Boolean): BaseNiceDialog {
        this.outCancel = outCancel
        return this
    }

    fun setAnimStyle(@StyleRes animStyle: Int): BaseNiceDialog {
        this.animStyle = animStyle
        return this
    }

    fun show(manager: FragmentManager): BaseNiceDialog {
        super.show(manager, System.currentTimeMillis().toString())
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
    }
}
