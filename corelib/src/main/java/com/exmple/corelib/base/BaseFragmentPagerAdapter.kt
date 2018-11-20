package com.exmple.corelib.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import java.lang.Long.valueOf

/**
 * 加载显示Fragment的ViewPagerAdapter基类
 * 提供可以刷新的方法
 *
 * @author Fly
 * @e-mail 1285760616@qq.com
 * @time 2018/3/22
 */
class BaseFragmentPagerAdapter(private var mFragmentManager: FragmentManager, var mFragments: ArrayList<Fragment>) : FragmentPagerAdapter(mFragmentManager) {
    private val mFragmentPositionMap: SparseArray<String> = SparseArray()
    private val mFragmentPositionMapAfterUpdate: SparseArray<String> = SparseArray()

    init {
        setFragmentPositionMap()
        setFragmentPositionMapForUpdate()
    }
    /**
     * 保存更新之前的位置信息，用<hashCode></hashCode>, position>的键值对结构来保存
     */
    private fun setFragmentPositionMap() {
        mFragmentPositionMap.clear()
        for (i in mFragments.indices) {
            mFragmentPositionMap.put(valueOf(getItemId(i)).toInt(), i.toString())
        }
    }

    /**
     * 保存更新之后的位置信息，用<hashCode></hashCode>, position>的键值对结构来保存
     */
    private fun setFragmentPositionMapForUpdate() {
        mFragmentPositionMapAfterUpdate.clear()
        for (i in mFragments.indices) {
            mFragmentPositionMapAfterUpdate.put(valueOf(getItemId(i)).toInt(), i.toString())
        }
    }

    /**
     * 在此方法中找到需要更新的位置返回POSITION_NONE，否则返回POSITION_UNCHANGED即可
     */
    override fun getItemPosition(obj: Any): Int {
        val hashCode = obj.hashCode()
        //查找object在更新后的列表中的位置
        val position = mFragmentPositionMapAfterUpdate.get(hashCode)
        //更新后的列表中不存在该object的位置了
        if (position == null) {
            return PagerAdapter.POSITION_NONE
        } else {
            //如果更新后的列表中存在该object的位置, 查找该object之前的位置并判断位置是否发生了变化
            val size = mFragmentPositionMap.size()
            for (i in 0 until size) {
                val key = mFragmentPositionMap.keyAt(i)
                if (key == hashCode) {
                    val index = mFragmentPositionMap.get(key)
                    return if (position == index) {
                        //位置没变依然返回POSITION_UNCHANGED
                        PagerAdapter.POSITION_UNCHANGED
                    } else {
                        //位置变了
                        PagerAdapter.POSITION_NONE
                    }
                }
            }
        }
        return PagerAdapter.POSITION_UNCHANGED
    }

    /**
     * 将指定的Fragment替换/更新为新的Fragment
     * @param oldFragment 旧Fragment
     * @param newFragment 新Fragment
     */
    fun replaceFragment(oldFragment: Fragment, newFragment: Fragment) {
        val position = mFragments.indexOf(oldFragment)
        if (position == -1) {
            return
        }
        //从Transaction移除旧的Fragment
        removeFragmentInternal(oldFragment)
        //替换List中对应的Fragment
        mFragments[position] = newFragment
        //刷新Adapter
        notifyItemChanged()
    }

    /**
     * 将指定位置的Fragment替换/更新为新的Fragment，同[.replaceFragment]
     * @param position    旧Fragment的位置
     * @param newFragment 新Fragment
     */
    fun replaceFragment(position: Int, newFragment: Fragment) {
        if (position < 0||position > mFragments.size - 1) {
            return
        }
        val oldFragment = mFragments[position]
        removeFragmentInternal(oldFragment)
        mFragments[position] = newFragment
        notifyItemChanged()
    }

    /**
     * 移除指定的Fragment
     * @param fragment 目标Fragment
     */
    fun removeFragment(fragment: Fragment) {
        if (mFragments.contains(fragment)) {
            //先从List中移除
            mFragments.remove(fragment)
            //然后从Transaction移除
            removeFragmentInternal(fragment)
            //最后刷新Adapter
            notifyItemChanged()
        }
    }

    /**
     * 移除指定位置的Fragment，同 [.removeFragment]
     * @param position
     */
    fun removeFragment(position: Int) {
        if (position < 0||position > mFragments.size - 1) {
            return
        }
        val fragment = mFragments[position]
        //然后从List中移除
        mFragments.remove(fragment)
        //先从Transaction移除
        removeFragmentInternal(fragment)
        //最后刷新Adapter
        notifyItemChanged()
    }

    /**
     * 添加Fragment,增加到末尾
     * @param fragment 目标Fragment
     */
    fun addFragment(fragment: Fragment) {
        mFragments.add(fragment)
        notifyItemChanged()
    }

    /**
     * 在指定位置插入一个Fragment
     * @param position 插入位置
     * @param fragment 目标Fragment
     */
    fun insertFragment(position: Int, fragment: Fragment) {
        if (position < 0||position > mFragments.size - 1) {
            return
        }
        mFragments.add(position, fragment)
        notifyItemChanged()
    }

    private fun notifyItemChanged() {
        //刷新之前重新收集位置信息
        setFragmentPositionMapForUpdate()
        notifyDataSetChanged()
        setFragmentPositionMap()
    }

    /**
     * 从Transaction移除Fragment
     * @param fragment 目标Fragment
     */
    private fun removeFragmentInternal(fragment: Fragment) {
        val transaction = mFragmentManager.beginTransaction()
        transaction.remove(fragment)
        transaction.commitNow()
    }

    /**
     * 此方法不用position做返回值即可破解fragment tag异常的错误
     */
    override fun getItemId(position: Int): Long {
        // 获取当前数据的hashCode，其实这里不用hashCode用自定义的可以关联当前Item对象的唯一值也可以，只要不是直接返回position
        return mFragments[position].hashCode().toLong()
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }
}