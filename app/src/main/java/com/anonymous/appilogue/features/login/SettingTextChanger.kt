package com.anonymous.appilogue.features.login

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.core.content.ContextCompat
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentEmailBinding
import com.anonymous.appilogue.databinding.FragmentNicknameBinding
import com.anonymous.appilogue.databinding.FragmentPasswordBinding
import com.google.android.material.textfield.TextInputEditText

object SettingTextChanger {
    private val viewModel = LoginViewModel()

    fun <B> TextInputEditText.setAddTextChangedListener(binding: B, viewModel: LoginViewModel, regex: Regex? = null) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                when (binding) {
                    is FragmentEmailBinding -> {
                        with(binding) {
                            when (this@setAddTextChangedListener) {
                                emailTextInputEditText -> {
                                    if (s!!.matches(regex!!)) {
                                        // 이메일 형식이 맞는 경우
                                        setCorrect(emailMoveNextButton, emailTextInputEditText, binding, viewModel, s)
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
                    is FragmentPasswordBinding -> {
                        with(binding) {
                            when (this@setAddTextChangedListener) {
                                passwordTextInputEditText -> {
                                    // 8자리 이상인 경우
                                    if (s!!.length >= 8) {
                                        setCorrect(passwordMoveNextButton, passwordTextInputEditText, binding, viewModel, s)
                                        // 보통 조건에 부합하는 경우 버튼 클릭 가능하지만 비밀번호는 확인 부분이 있어 한번 더 클릭 못하게 바꿔줍니다
                                        FirstButtonInit.buttonInit(passwordMoveNextButton)
                                    }
                                    // 8자보다 짧을 경우
                                    else {
                                        setIncorrect(passwordMoveNextButton, passwordTextInputEditText, resources.getString(R.string.password_length))
                                    }
                                }
                                passwordTextInputEditTextBelow -> {
                                    // 비밀번호가 같다면
                                    if (s!!.toString() == viewModel.password.value) {
                                        setCorrect(passwordMoveNextButton, passwordTextInputEditTextBelow, binding, viewModel, s)
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
                                        setCorrect(nicknameDoneButton, nicknameTextInputEditText, binding, viewModel, s!!)
                                    } else {
                                        // 서버에 같은 닉네임이 있다면 혹은 닉네임 길이가 0이라면 까지 추가해야합니다.
                                        setIncorrect(nicknameDoneButton, nicknameTextInputEditText, resources.getString(R.string.nickname_be_used))
                                    }
                                }
                            }
                        }
                    }

                }
            }
        })

    }

    private fun <B> setCorrect(button: Button, textInputEditText: TextInputEditText, binding: B, viewModel: LoginViewModel, s: Editable) {
        button.isEnabled = true
        textInputEditText.error = null
        with(button) {
            context?.let { ctx ->
                setTextColor(ContextCompat.getColor(ctx, R.color.white))
                background = ContextCompat.getDrawable(ctx, R.drawable.border_radius_12_purple)
                background.setTint(ContextCompat.getColor(ctx, R.color.purple_01))
            }
        }
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
        with(button) {
            context?.let { ctx ->
                setTextColor(ContextCompat.getColor(ctx, R.color.gray_01))
                background.setTint(ContextCompat.getColor(ctx, R.color.gray_02))
            }
        }
    }

}
