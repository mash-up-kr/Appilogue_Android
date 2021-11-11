package com.anonymous.appilogue.features.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentPasswordBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.login.SettingTextChanger.setAddTextChangedListener

class PasswordFragment :
    BaseFragment<FragmentPasswordBinding, LoginViewModel>(R.layout.fragment_password) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            with(passwordMoveNextButton) {
                FirstButtonInit.buttonInit(this)
                binding.passwordWrongPasswordNotification.visibility = View.GONE
                if (viewModel.lostPassword) {
                    binding.passwordEnterPasswordText.text = "비밀번호 재설정"
                    setOnClickListener {
                        it.findNavController().navigate(R.id.action_passwordFragment_to_loginEmailFragment)
                    }
                } else {
                    binding.passwordEnterPasswordText.text = getString(R.string.password_text)
                    setOnClickListener {
                        it.findNavController().navigate(R.id.action_passwordFragment_to_nicknameFragment)
                    }
                }
            }

            passwordEdittext.setAddTextChangedListener(binding, viewModel)
            passwordEdittextBelow.setAddTextChangedListener(binding, viewModel)
        }
    }
}




