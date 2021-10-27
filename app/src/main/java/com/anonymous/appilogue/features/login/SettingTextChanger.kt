package com.anonymous.appilogue.features.login

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentEmailBinding
import com.anonymous.appilogue.databinding.FragmentNicknameBinding
import com.anonymous.appilogue.databinding.FragmentPasswordBinding
import com.google.android.material.textfield.TextInputEditText

object SettingTextChanger {
    private val viewModel = LoginViewModel()

    fun <B> TextInputEditText.setAddTextChangedListener(binding: B, regex: Regex? = null) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                when (binding) {
                    is FragmentPasswordBinding -> {
                        with(binding) {
                            when (this@setAddTextChangedListener) {
                                passwordTextInputEditText -> {
                                    // 8자리 이상인 경우
                                    if (s!!.length >= 8) {
                                        setCorrect(passwordMoveNextButton, passwordTextInputEditText, binding, s)
                                    }
                                    // 8자보다 짧을 경우
                                    else {
                                        setIncorrect(passwordMoveNextButton, passwordTextInputEditText, resources.getString(R.string.password_length))
                                    }
                                }
                                passwordTextInputEditTextBelow -> {
                                    // 비밀번호가 같다면
                                    if (s!!.toString() == viewModel.password.value) {
                                        setCorrect(passwordMoveNextButton, passwordTextInputEditTextBelow, binding, s)
                                    }
                                    // 비밀번호가 다르다면
                                    else {
                                        setIncorrect(
                                            passwordMoveNextButton,
                                            passwordTextInputEditTextBelow,
                                            resources.getString(R.string.password_check)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    is FragmentNicknameBinding -> {
                        with(binding) {
                            when (this@setAddTextChangedListener) {
                                nicknameTextInputEditText -> {
                                    if (true) {  // 임시 데이터 true 입니당 서버에서 값을 가져와서 확인해야 합니다
                                        setCorrect(nicknameDoneButton, nicknameTextInputEditText, binding, s!!)
                                    } else {
                                        // 서버에 같은 닉네임이 있다면
                                        setIncorrect(nicknameDoneButton, nicknameTextInputEditText, resources.getString(R.string.nickname_be_used))
                                    }
                                }
                            }
                        }
                    }
                    is FragmentEmailBinding -> {
                        with(binding) {
                            when (this@setAddTextChangedListener) {
                                emailTextInputEditText -> {
                                    if (s!!.matches(regex!!)) {
                                        // 이메일 형식이 맞는 경우
                                        setCorrect(emailMoveNextButton, emailTextInputEditText, binding, s)
                                    } else {
                                        // 이메일 형식이 아닌 경우
                                        setIncorrect(
                                            emailMoveNextButton,
                                            emailTextInputEditText,
                                            resources.getString(R.string.email_format_error_text)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })

    }

    private fun <B> setCorrect(button: Button, textInputEditText: TextInputEditText, binding: B, s: Editable) {
        button.isEnabled = true
        textInputEditText.error = null
        when (binding) {
            is FragmentEmailBinding -> viewModel.emailAddress.value = s.toString()
            is FragmentNicknameBinding -> viewModel.nickName.value = s.toString()
            is FragmentPasswordBinding -> {
                if (textInputEditText == binding.passwordTextInputEditText) {
                    viewModel.password.value = s.toString()
                } else if (textInputEditText == binding.passwordTextInputEditTextBelow) {
                    viewModel.password.value = s.toString()
                }
            }
        }
    }

    private fun setIncorrect(button: Button, textInputEditText: TextInputEditText, errorText: String) {
        button.isEnabled = false
        textInputEditText.error = errorText
    }

}
