package com.anonymous.appilogue.features.base

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IndexOutOfBoundsException

class ViewPagerAdapter(
    activity: FragmentActivity,
    private val fragmentCreators: SparseArray<() -> Fragment>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = fragmentCreators.size()

    override fun createFragment(position: Int): Fragment =
        fragmentCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}