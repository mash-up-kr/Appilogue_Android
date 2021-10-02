package com.anonymous.appilogue.features.login

import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentPasswordBinding
import com.anonymous.appilogue.features.base.BaseFragment

class PasswordFragment :
    BaseFragment<FragmentPasswordBinding, LoginViewModel>(R.layout.fragment_password) {
    override val viewModel = LoginViewModel()
}