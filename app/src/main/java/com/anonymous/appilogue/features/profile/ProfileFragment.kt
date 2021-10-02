package com.anonymous.appilogue.features.profile

import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentProfileBinding
import com.anonymous.appilogue.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {
    override val viewModel: ProfileViewModel
        get() = TODO("Not yet implemented")
}