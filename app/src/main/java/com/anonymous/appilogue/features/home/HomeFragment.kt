package com.anonymous.appilogue.features.home

import android.os.Bundle
import android.view.View
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentHomeBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomSheet()
        bind {
            homeViewModel = viewModel
        }
    }

    private fun initBottomSheet() {
        binding.bottomSheetHome.root.apply {
            updateLayoutParams {
                height = bottomSheetExpandedHeight
            }
            BottomSheetBehavior.from(this).apply {
                peekHeight = bottomSheetPeekHeight
                viewModel.changeBottomSheetState(BottomSheetBehavior.STATE_HIDDEN)
                addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        viewModel.changeBottomSheetState(newState)
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                })
            }
            viewModel.bottomSheetState.observe(viewLifecycleOwner, { newState ->
                BottomSheetBehavior.from(this).apply {
                    state = newState
                }
            })
            viewModel.bottomSheetHideable.observe(viewLifecycleOwner, { bottomSheetHideable ->
                BottomSheetBehavior.from(this).apply {
                    isHideable = bottomSheetHideable
                }
            })
        }
    }

    companion object {
        const val bottomSheetPeekHeight = 600
        const val bottomSheetExpandedHeight = 2000
    }
}
