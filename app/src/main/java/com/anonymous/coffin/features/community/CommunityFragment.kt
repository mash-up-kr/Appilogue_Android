package com.anonymous.coffin.features.community

import com.anonymous.coffin.R
import com.anonymous.coffin.databinding.FragmentCommunityBinding
import com.anonymous.coffin.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment :
    BaseFragment<FragmentCommunityBinding, CommunityViewModel>(R.layout.fragment_community) {
    override val viewModel: CommunityViewModel
        get() = TODO("Not yet implemented")
}