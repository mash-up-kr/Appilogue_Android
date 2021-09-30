package com.anonymous.coffin.features.home

import com.anonymous.coffin.R
import com.anonymous.coffin.databinding.FragmentHomeBinding
import com.anonymous.coffin.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel
        get() = TODO("Not yet implemented")
}