package com.anonymous.appilogue.features.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentEmailBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.login.SettingTextChanger.setAddTextChangedListener

class EmailFragment :
    BaseFragment<FragmentEmailBinding, LoginViewModel>(R.layout.fragment_email) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val regex = "\\w+@\\w+.(com|net|COM|NET)".toRegex()

        with(binding) {
            with(emailMoveNextButton) {
                FirstButtonInit.buttonInit(this)
                setOnClickListener {
                    viewModel.timerStart()
                    it.findNavController().navigate(R.id.action_emailFragment_to_certificationFragment)
                }
            }

            emailBackButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_emailFragment_to_loginFragment)
            }

            emailCloseButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_emailFragment_to_loginFragment)
            }

            emailTextInputEditText.setAddTextChangedListener(binding, regex)
        }
    }
}
