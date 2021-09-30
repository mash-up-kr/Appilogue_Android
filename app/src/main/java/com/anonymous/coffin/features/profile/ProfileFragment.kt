package com.anonymous.coffin.features.profile

import com.anonymous.coffin.R
import com.anonymous.coffin.databinding.FragmentProfileBinding
import com.anonymous.coffin.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {
    override val viewModel: ProfileViewModel
        get() = TODO("Not yet implemented")
}