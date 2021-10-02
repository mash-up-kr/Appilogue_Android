package com.anonymous.appilogue.features.login

import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentNicknameBinding
import com.anonymous.appilogue.features.base.BaseFragment

class NicknameFragment :
    BaseFragment<FragmentNicknameBinding, LoginViewModel>(R.layout.fragment_nickname) {
    override val viewModel = LoginViewModel()
}