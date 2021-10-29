package com.anonymous.appilogue.features.home

import android.content.res.Resources
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentHomeBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.home.bottomsheet.AppFragment
import com.anonymous.appilogue.features.home.bottomsheet.AppFragment.Companion.BLACK_HOLE
import com.anonymous.appilogue.features.home.bottomsheet.AppFragment.Companion.WHITE_HOLE
import com.anonymous.appilogue.features.home.bottomsheet.BottomSheetPagerAdapter
import com.anonymous.appilogue.features.home.bottomsheet.MyDecorationFragment
import com.anonymous.appilogue.features.home.bottomsheet.StoreFragment
import com.anonymous.appilogue.features.home.onboarding.OnboardingFragment
import com.anonymous.appilogue.features.main.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val starsByFocus = EnumMap<Focus, ImageView>(Focus::class.java)

    override val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            homeViewModel = viewModel
        }
        initBottomSheet()
        observeStar()
        initStarByFocus()
        SpaceAnimator.animateSpace(binding.ivSpace)
        viewModel.changeFocus(Focus.None)
        initOnboarding()
    }

    private fun initOnboarding() {
        PreferenceManager.getDefaultSharedPreferences(context).apply {
            if (true) {
                childFragmentManager.commit {
                    add<OnboardingFragment>(R.id.fcv_onboarding)
                    setReorderingAllowed(true)
                }
            }
        }
    }

    private fun getViewPagerTabTextAndFragments(focus: Focus): SparseArray<Pair<Int, () -> Fragment>> {
        val viewPagerFragments = SparseArray<Pair<Int, () -> Fragment>>()
        when (focus) {
            Focus.OnBlackHole -> viewPagerFragments.apply {
                put(
                    FIRST_TAB,
                    Pair(R.string.my_black_hole, { AppFragment.newInstance(BLACK_HOLE) })
                )
            }
            Focus.OnWhiteHole -> viewPagerFragments.apply {
                put(
                    FIRST_TAB,
                    Pair(R.string.my_white_hole, { AppFragment.newInstance(WHITE_HOLE) })
                )
            }
            Focus.OnPlanet -> viewPagerFragments.apply {
                put(FIRST_TAB, Pair(R.string.my_planet, { MyDecorationFragment.newInstance() }))
                put(SECOND_TAB, Pair(R.string.planet_store, { StoreFragment.newInstance() }))
            }
            Focus.OnSpaceDust -> viewPagerFragments.apply {
                put(FIRST_TAB, Pair(R.string.my_space_dust, { MyDecorationFragment.newInstance() }))
                put(SECOND_TAB, Pair(R.string.dust_store, { StoreFragment.newInstance() }))
            }
        }
        return viewPagerFragments
    }

    private fun initStarByFocus() {
        with(binding) {
            listOf(
                Pair(Focus.OnBlackHole, ivBlackHole),
                Pair(Focus.OnWhiteHole, ivWhiteHole),
                Pair(Focus.OnPlanet, ivMainPlanet),
                Pair(Focus.OnSpaceDust, ivSpaceDust),
                Pair(Focus.OffBlackHole, ivBlackHole),
                Pair(Focus.OffWhiteHole, ivWhiteHole),
                Pair(Focus.OffPlanet, ivMainPlanet),
                Pair(Focus.OffSpaceDust, ivSpaceDust)
            ).onEach {
                starsByFocus[it.first] = it.second
            }
        }
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
                        if (newState != BottomSheetBehavior.STATE_HIDDEN) {
                            mainViewModel.hideBottomNavigation()
                        } else {
                            mainViewModel.showBottomNavigation()
                        }
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
                binding.bottomSheetHome.apply {
                    val viewPagerTabTextAndFragments = getViewPagerTabTextAndFragments(it)
                    vpBottomSheetViewPager.adapter =
                        BottomSheetPagerAdapter(this@HomeFragment, viewPagerTabTextAndFragments)
                    TabLayoutMediator(tlBottomSheetTab, vpBottomSheetViewPager) { tab, position ->
                        tab.text = getString(viewPagerTabTextAndFragments[position].first)
                    }.attach()
                }
                starsByFocus[it]?.let { star ->
                    SpaceAnimator.focusStar(star, starsByFocus.values.toSet().toList(), true)
                }
            } else if (Focus.isOffFocus(it)) {
                starsByFocus[it]?.let { star ->
                    SpaceAnimator.focusStar(star, starsByFocus.values.toSet().toList(), false)
                }
            }
        })
    }

    companion object {
        const val FIRST_TAB = 0
        const val SECOND_TAB = 1
    }
}