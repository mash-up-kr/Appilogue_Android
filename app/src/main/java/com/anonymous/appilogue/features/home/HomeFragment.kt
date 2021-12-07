package com.anonymous.appilogue.features.home

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentHomeBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.home.bottomsheet.*
import com.anonymous.appilogue.features.home.bottomsheet.hole.HoleFragment
import com.anonymous.appilogue.features.main.MainActivity
import com.anonymous.appilogue.features.home.bottomsheet.hole.HoleFragment.Companion.BLACK_HOLE
import com.anonymous.appilogue.features.home.bottomsheet.hole.HoleFragment.Companion.WHITE_HOLE
import com.anonymous.appilogue.features.home.bottomsheet.planet.MyPlanetFragment
import com.anonymous.appilogue.features.home.bottomsheet.space_dust.MySpaceDustFragment
import com.anonymous.appilogue.features.home.bottomsheet.space_dust.MySpaceDustViewModel
import com.anonymous.appilogue.features.home.bottomsheet.space_dust.StoreFragment
import com.anonymous.appilogue.features.home.onboarding.OnboardingFragment
import com.anonymous.appilogue.features.main.MainViewModel
import com.anonymous.appilogue.preference.AppilogueSharedPreferences
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    @Inject
    lateinit var sharedPreference: AppilogueSharedPreferences

    private val mainViewModel: MainViewModel by activityViewModels()
    private val _mySpaceDustViewModel: MySpaceDustViewModel by activityViewModels()
    override val viewModel: HomeViewModel by activityViewModels()

    private val starsByFocus = EnumMap<Focus, ImageView>(Focus::class.java)
    private val actionByFocus = EnumMap<Focus, () -> Unit>(Focus::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).showBottomNavigation()

        bind {
            homeViewModel = viewModel
            mySpaceDustViewModel = _mySpaceDustViewModel
        }
        initBottomSheet()
        observeStar()
        initStarByFocus()
        initActionByFocus()
        binding.ivAlarm.setOnClickListener {
            (activity as MainActivity).navigateTo(R.id.alarmFragment)
        }
        SpaceAnimator.animateSpace(binding.ivSpace)
        viewModel.changeFocus(Focus.None)
        initOnboarding()
        observeToast()
    }

    private fun observeToast() {
        lifecycleScope.launch {
            _mySpaceDustViewModel.toastEvent.collect {
                viewModel.showSaveSuccessToast()
            }
        }
        initOnBoarding()
    }

    private fun initOnBoarding() {
        PreferenceManager.getDefaultSharedPreferences(context).apply {
            if (!getBoolean(COMPLETED_ONBOARDING, false)) {
                childFragmentManager.commit {
                    add<OnboardingFragment>(R.id.fcv_onboarding)
                    setReorderingAllowed(true)
                }
    private fun initOnboarding() {
        if (!sharedPreference.getOnboardingIsDone()) {
            childFragmentManager.commit {
                add<OnboardingFragment>(R.id.fcv_home)
                setReorderingAllowed(true)
            }
        }
    }

    private fun getViewPagerTabTextAndFragments(focus: Focus): SparseArray<Pair<Int, () -> Fragment>> {
        val viewPagerFragments = SparseArray<Pair<Int, () -> Fragment>>()
        when (focus) {
            Focus.OnBlackHole -> viewPagerFragments.apply {
                put(
                    FIRST_TAB,
                    Pair(R.string.my_black_hole, { HoleFragment.newInstance(BLACK_HOLE) })
                )
            }
            Focus.OnWhiteHole -> viewPagerFragments.apply {
                put(
                    FIRST_TAB,
                    Pair(R.string.my_white_hole, { HoleFragment.newInstance(WHITE_HOLE) })
                )
            }
            Focus.OnPlanet -> viewPagerFragments.apply {
                put(FIRST_TAB, Pair(R.string.my_planet, { MyPlanetFragment.newInstance() }))
                put(SECOND_TAB, Pair(R.string.planet_store, { StoreFragment.newInstance() }))
            }
            Focus.OnSpaceDust -> viewPagerFragments.apply {
                put(FIRST_TAB, Pair(R.string.my_space_dust, { MySpaceDustFragment.newInstance() }))
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

    private fun initActionByFocus() {
        listOf(
            Pair(
                Focus.OnSpaceDust,
                {
                    BottomSheetBehavior.from(binding.bottomSheetHome.root).apply {
                        isDraggable = false
                    }
                    setBottomSheetPeekHeight(R.dimen.peek_bottomsheet_home_space_dust)
                }),
            Pair(
                Focus.OffSpaceDust,
                {
                    BottomSheetBehavior.from(binding.bottomSheetHome.root).apply {
                        isDraggable = true
                    }
                    mainViewModel.fetchMyInformation()
                    setBottomSheetPeekHeight(R.dimen.peek_bottomsheet_home_default)
                }
            )
        ).onEach {
            actionByFocus[it.first] = it.second
        }
    }

    private fun initBottomSheet() {
        binding.bottomSheetHome.root.apply {
            updateLayoutParams {
                height = Resources.getSystem().displayMetrics.heightPixels -
                        resources.getDimension(R.dimen.expanded_bottomsheet_home_margin).toInt()
            }
            BottomSheetBehavior.from(this).apply {
                peekHeight = (resources.getDimension(R.dimen.peek_bottomsheet_home_default)).toInt()
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
        viewModel.starFocused.observe(viewLifecycleOwner, { focus ->
            actionByFocus[focus]?.invoke()
            if (Focus.isOnFocus(focus)) {
                binding.bottomSheetHome.apply {
                    val viewPagerTabTextAndFragments = getViewPagerTabTextAndFragments(focus)
                    vpBottomSheetViewPager.adapter =
                        BottomSheetPagerAdapter(this@HomeFragment, viewPagerTabTextAndFragments)
                    TabLayoutMediator(tlBottomSheetTab, vpBottomSheetViewPager) { tab, position ->
                        tab.text = getString(viewPagerTabTextAndFragments[position].first)
                    }.attach()
                }
                starsByFocus[focus]?.let { star ->
                    SpaceAnimator.focusStar(star, starsByFocus.values.toSet().toList(), true)
                }
            } else if (Focus.isOffFocus(focus)) {
                starsByFocus[focus]?.let { star ->
                    SpaceAnimator.focusStar(star, starsByFocus.values.toSet().toList(), false)
                }
            }
        })
    }

    private fun setBottomSheetPeekHeight(heightDimensionId: Int) {
        BottomSheetBehavior.from(binding.bottomSheetHome.root).apply {
            peekHeight =
                (resources.getDimension(heightDimensionId)).toInt()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).showBottomNavigation()
    }

    companion object {
        const val FIRST_TAB = 0
        const val SECOND_TAB = 1
    }
}