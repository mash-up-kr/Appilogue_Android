package com.anonymous.appilogue.features.home.bottomsheet

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BottomSheetPagerAdapter(
    fragment: Fragment,
    private val viewPagerFragments: SparseArray<Pair<Int, () -> Fragment>>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = viewPagerFragments.size()

    override fun createFragment(position: Int): Fragment {
        return viewPagerFragments[position].second.invoke()
    }
}