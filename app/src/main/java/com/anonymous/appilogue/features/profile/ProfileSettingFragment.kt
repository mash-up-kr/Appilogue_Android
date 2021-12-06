package com.anonymous.appilogue.features.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentProfileSettingBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity
import com.anonymous.appilogue.features.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileSettingFragment :
    BaseFragment<FragmentProfileSettingBinding, MainViewModel>(R.layout.fragment_profile_setting) {
    override val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            mainViewModel = viewModel
        }
        with(binding) {
            ivBack.setOnClickListener {
                (activity as MainActivity).navigateTo(R.id.profileFragment)
            }
        }
    }

}