package com.anonymous.appilogue.features.home

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentHomeBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomSheet()
    }

    private fun initBottomSheet() {
        binding.ivMainPlanet.setOnClickListener {
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility =
                View.GONE
            BottomSheetBehavior.from(binding.bottomSheetHome.root).apply {
                state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        binding.bottomSheetHome.root.apply {
            updateLayoutParams {
                height = 600
                // TODO
            }
            BottomSheetBehavior.from(this).apply {
                state = BottomSheetBehavior.STATE_HIDDEN
                addBottomSheetCallback(
                    object : BottomSheetBehavior.BottomSheetCallback() {
                        override fun onStateChanged(bottomSheet: View, newState: Int) {
                            // TODO
                        }

                        override fun onSlide(bottomSheet: View, slideOffset: Float) {
                            // TODO
                        }
                    })
            }
        }
    }
}