package com.anonymous.appilogue.features.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentLoginBinding
import com.anonymous.appilogue.features.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(R.layout.fragment_login) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.btSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_emailFragment)
        }
        binding.btEmailLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_loginEmailFragment)
        }
    }
}
