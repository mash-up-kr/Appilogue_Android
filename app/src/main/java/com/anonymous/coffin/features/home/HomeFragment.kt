package com.anonymous.coffin.features.home

import android.os.Bundle
import android.view.View
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import com.anonymous.coffin.R
import com.anonymous.coffin.databinding.FragmentHomeBinding
import com.anonymous.coffin.features.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            homeViewModel = viewModel
        }
        binding.ivMainPlanet.setOnClickListener {
            BottomSheetBehavior.from(binding.bottomSheetHome.root).run {
                state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        initBottomSheet()
    }

    private fun initBottomSheet() {
        setBottomSheetHeight()
        BottomSheetBehavior.from(binding.bottomSheetHome.root)
            .addBottomSheetCallback(
                object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        // TODO
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        // TODO
                    }
                })
    }

    private fun setBottomSheetHeight() {
        binding.bottomSheetHome.root.updateLayoutParams {
            height = 600
            // TODO
        }
    }
}