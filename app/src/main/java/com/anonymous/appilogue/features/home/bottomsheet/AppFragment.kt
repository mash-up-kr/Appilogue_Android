package com.anonymous.appilogue.features.home.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ListAdapter
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentAppBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.home.BottomSheetAppsRecyclerViewDecoration
import com.anonymous.appilogue.features.home.HomeViewModel
import com.anonymous.appilogue.model.Review
import com.anonymous.appilogue.utils.replaceList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AppFragment :
    BaseFragment<FragmentAppBinding, AppViewModel>(R.layout.fragment_app) {
    override val viewModel: AppViewModel by viewModels()
    private val bottomSheetAppAdapter: BottomSheetAppAdapter by lazy {
        BottomSheetAppAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            appViewModel = viewModel
            rvBottomSheetApps.apply {
                adapter = bottomSheetAppAdapter
                addItemDecoration(BottomSheetAppsRecyclerViewDecoration(context))
            }
        }
        arguments?.let {
            fetchApps(it)
        }
    }

    private fun fetchApps(hole: Bundle) {
        if (hole.get(HOLE) == BLACK_HOLE) {
            viewModel.fetchMyBlackHoleApps()
        } else if (hole.get(HOLE) == WHITE_HOLE) {
            viewModel.fetchMyWhiteHoleApps()
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