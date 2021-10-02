package com.anonymous.appilogue.features.community

import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentCommunityBinding
import com.anonymous.appilogue.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment :
    BaseFragment<FragmentCommunityBinding, CommunityViewModel>(R.layout.fragment_community) {
    override val viewModel: CommunityViewModel
        get() = TODO("Not yet implemented")
}