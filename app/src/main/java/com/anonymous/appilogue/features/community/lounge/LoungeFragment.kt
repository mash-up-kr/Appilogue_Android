package com.anonymous.appilogue.features.community.lounge

import android.content.Context
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentLoungeBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.base.ViewPagerAdapter
import com.anonymous.appilogue.features.community.reviewlist.ReviewListFragment
import com.anonymous.appilogue.features.community.reviewlist.ReviewListFragment.Companion.BLACK_HOLE
import com.anonymous.appilogue.features.community.reviewlist.ReviewListFragment.Companion.LOUNGE_FRAGMENT
import com.anonymous.appilogue.features.community.reviewlist.ReviewListFragment.Companion.WHITE_HOLE
import com.anonymous.appilogue.features.main.MainActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoungeFragment
    : BaseFragment<FragmentLoungeBinding, LoungeViewModel>(R.layout.fragment_lounge) {

    override val viewModel: LoungeViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).showBottomNavigation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.viewPager.apply {
            adapter = ViewPagerAdapter(activity as FragmentActivity, getFragmentCreators())
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                BLACK_HOLE_PAGE_INDEX -> {
                    tab.setText(R.string.black_hole)
                }
                WHITE_HOLE_PAGE_INDEX -> {
                    tab.setText(R.string.white_hole)
                }
            }
        }.attach()
    }

    private fun getFragmentCreators() = SparseArray<() -> Fragment>().apply {
        put(BLACK_HOLE_PAGE_INDEX) { ReviewListFragment.newInstance(LOUNGE_FRAGMENT, BLACK_HOLE) }
        put(WHITE_HOLE_PAGE_INDEX) { ReviewListFragment.newInstance(LOUNGE_FRAGMENT, WHITE_HOLE) }
    }

    companion object {
        private const val BLACK_HOLE_PAGE_INDEX = 0
        private const val WHITE_HOLE_PAGE_INDEX = 1
    }
}