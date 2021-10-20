package com.anonymous.appilogue.features.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentEmailBinding
import com.anonymous.appilogue.features.base.BaseFragment

class EmailFragment : BaseFragment<FragmentEmailBinding, LoginViewModel>(R.layout.fragment_email) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val regex = "\\w+@\\w+.(com|net)".toRegex()

        with(binding) {
            emailMoveNextButton.isEnabled = false
            emailBackButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_emailFragment_to_loginFragment)
            }
            emailCloseButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_emailFragment_to_loginFragment)
            }
            emailMoveNextButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_emailFragment_to_certificationFragment)
            }
            textInputEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // do nothing
                }

                override fun afterTextChanged(s: Editable?) {
                    // 이메일 형식일 경우
                    if (s!!.matches(regex)) {
                        binding.emailMoveNextButton.isEnabled = true
                        binding.textInputLayout.error = null
                    } else {
                        // 이메일 형식이 아닌 경
                        binding.emailMoveNextButton.isEnabled = false
                        binding.textInputLayout.error = getString(R.string.email_format_error_text)
                    }
                }
            })
        }
    }
}
