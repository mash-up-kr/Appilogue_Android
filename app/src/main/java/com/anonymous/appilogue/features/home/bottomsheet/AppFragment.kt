package com.anonymous.appilogue.features.home.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentAppBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.home.BottomSheetAppsRecyclerViewDecoration
import com.anonymous.appilogue.features.home.HomeViewModel

class AppFragment() :
    BaseFragment<FragmentAppBinding, HomeViewModel>(R.layout.fragment_app) {
    override val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            homeViewModel = viewModel
            rvBottomSheetApps.apply {
                adapter = BottomSheetAppAdapter()
                addItemDecoration(BottomSheetAppsRecyclerViewDecoration(context))
            }
        }
        arguments?.let {
            fetchApps(it)
        }
    }

    private fun fetchApps(hole: Bundle) {
        if (hole.get(HOLE) == BLACK_HOLE) {
            viewModel.fetchBlackHoleApps()
        } else if (hole.get(HOLE) == WHITE_HOLE) {
            viewModel.fetchWhiteHoleApps()
        }
    }

    companion object {
        fun newInstance(hole: String): Fragment {
            val fragment = AppFragment()
            fragment.arguments = Bundle().apply {
                putString(HOLE, hole)
            }
            return fragment
        }

        private const val HOLE = "HOLE"
        const val WHITE_HOLE = "WHITE_HOLE"
        const val BLACK_HOLE = "BLACK_HOLE"
    }
}