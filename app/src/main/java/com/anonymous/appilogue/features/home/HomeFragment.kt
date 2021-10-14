package com.anonymous.appilogue.features.home

import android.content.res.Resources
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

    private val starsByFocus by lazy {
        with(binding) {
            hashMapOf(
                Pair(Focus.OnBlackHole, ivBlackHole),
                Pair(Focus.OffBlackHole, ivBlackHole),
                Pair(Focus.OnWhiteHole, ivWhiteHole),
                Pair(Focus.OffWhiteHole, ivWhiteHole),
                Pair(Focus.OffPlanet, ivMainPlanet),
                Pair(Focus.OnPlanet, ivMainPlanet),
                Pair(Focus.OnSpaceDust, ivSpaceDust),
                Pair(Focus.OffSpaceDust, ivSpaceDust)
            )
        }
    }

    override val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            homeViewModel = viewModel
        }
        initBottomSheet()
        observeStar()
        SpaceManager.stars = starsByFocus.values.toSet().toList()
        SpaceManager.animateSpace(binding.ivSpace)
    }

    private fun initBottomSheet() {
        binding.bottomSheetHome.root.apply {
            updateLayoutParams {
                height = Resources.getSystem().displayMetrics.heightPixels -
                        resources.getDimension(R.dimen.expanded_bottomsheet_home_margin).toInt()
            }
            BottomSheetBehavior.from(this).apply {
                peekHeight = (resources.getDimension(R.dimen.peek_bottomsheet_home)).toInt()
                viewModel.changeBottomSheetState(BottomSheetBehavior.STATE_HIDDEN)
                addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        viewModel.changeBottomSheetState(newState)
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        listOf(binding.ivBackButton, binding.tvBackToHome).forEach {
                            it.alpha = slideOffset + 1
                        }
                    }
                })
            }
            viewModel.bottomSheetHideable.observe(viewLifecycleOwner, { bottomSheetHideable ->
                BottomSheetBehavior.from(this).apply {
                    isHideable = bottomSheetHideable
                }
            })
        }
    }

    private fun observeStar() {
        viewModel.starFocused.observe(viewLifecycleOwner, {
            if (Focus.isOnFocus(it)) {
                starsByFocus[it]?.let { star ->
                    SpaceManager.focusStar(star, true)
                }
            }
            if (Focus.isOffFocus(it)) {
                starsByFocus[it]?.let { star ->
                    SpaceManager.focusStar(star, false)
                }
            }
        })
    }
}