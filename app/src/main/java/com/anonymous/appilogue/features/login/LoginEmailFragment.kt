package com.anonymous.appilogue.features.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentEmailLoginBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.login.SettingTextChanger.setAddTextChangedListener
import com.anonymous.appilogue.features.main.MainActivity

class LoginEmailFragment :
    BaseFragment<FragmentEmailLoginBinding, LoginViewModel>(R.layout.fragment_email_login) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val regex = "\\w+@\\w+.(com|net|COM|NET)".toRegex()

        initView()
        binding.emailLoginEmail.setAddTextChangedListener(binding, viewModel, regex)
        binding.emailLoginPassword.setAddTextChangedListener(binding, viewModel)

    }

    private fun initView() {
        binding.emailLoginMoveNextButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
