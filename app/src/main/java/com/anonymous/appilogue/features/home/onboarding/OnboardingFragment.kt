package com.anonymous.appilogue.features.home.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceManager
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentOnboardingBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.home.HomeViewModel
import com.anonymous.appilogue.features.main.MainViewModel

class OnboardingFragment :
    BaseFragment<FragmentOnboardingBinding, OnboardingViewModel>(R.layout.fragment_onboarding) {

    val mainViewModel: MainViewModel by activityViewModels()
    val homeViewModel: HomeViewModel by activityViewModels()
    override val viewModel: OnboardingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel.disableClickBottomNavigation()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainViewModel.enableClickBottomNavigation()
        PreferenceManager.getDefaultSharedPreferences(context).edit().apply {
            putBoolean(COMPLETED_ONBOARDING, true)
            apply()
        }
    }

    companion object {
        const val COMPLETED_ONBOARDING = "ONBOARDING"
    }
}