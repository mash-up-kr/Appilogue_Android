package com.anonymous.appilogue.features.login

import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentEmailBinding
import com.anonymous.appilogue.databinding.FragmentEmailLoginBinding
import com.anonymous.appilogue.databinding.FragmentNicknameBinding
import com.anonymous.appilogue.databinding.FragmentPasswordBinding

object SettingTextChanger {
    fun <B> EditText.setAddTextChangedListener(binding: B, viewModel: LoginViewModel, regex: Regex? = null) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when (binding) {
                    is FragmentNicknameBinding -> {
                        binding.nicknameCounting.text = "${binding.nicknameInputText.length()}/10"
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                when (binding) {
                    is FragmentEmailBinding -> {
                        with(binding) {
                            when (this@setAddTextChangedListener) {
                                emailSubmitEditText -> {
                                    if (s!!.matches(regex!!)) {
                                        // 이메일 형식이 맞는 경우
                                        setCorrect(emailMoveNextButton, emailSubmitEditText, emailExplainText, binding, viewModel, s)
                                    } else {
                                        // 이메일 형식이 아닌 경우
                                        setIncorrect(
                                            emailMoveNextButton,
                                            emailSubmitEditText,
                                            emailExplainText,
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
                                passwordEdittext -> {
                                    // 아래로 포커스 이동시에 백그라운드 변경 (포커스가 아니라 입력시라는 점은 추후에 수정..)
                                    passwordEdittextBelow.setBackgroundResource(R.drawable.border_radius_10)
                                    // 8자리 이상인 경우
                                    if (s!!.length >= 8) {
                                        setCorrect(passwordMoveNextButton, passwordEdittext, null, binding, viewModel, s)
                                        // 보통 조건에 부합하는 경우 버튼 클릭 가능하지만 비밀번호는 확인 부분이 있어 한번 더 클릭 못하게 바꿔줍니다
                                        FirstButtonInit.buttonInit(passwordMoveNextButton)
                                    }
                                    // 8자보다 짧을 경우
                                    else {
                                        setIncorrect(
                                            passwordMoveNextButton,
                                            passwordEdittext,
                                            null,
                                            resources.getString(R.string.password_length)
                                        )
                                    }
                                }
                                passwordEdittextBelow -> {
                                    // 위로 포커스 이동시에 백그라운드 변경 (포커스가 아니라 입력시라는 점은 추후에 수정..)
                                    passwordEdittext.setBackgroundResource(R.drawable.border_radius_10)
                                    // 비밀번호가 같다면
                                    if (s!!.toString() == viewModel.password.value) {
                                        setCorrect(passwordMoveNextButton, passwordEdittextBelow, null, binding, viewModel, s)
                                        binding.passwordWrongPasswordNotification.visibility = GONE
                                    }
                                    // 비밀번호가 다르다면
                                    else {
                                        setIncorrect(
                                            passwordMoveNextButton,
                                            passwordEdittextBelow,
                                            null,
                                            resources.getString(R.string.password_check)
                                        )
                                        binding.passwordWrongPasswordNotification.visibility = VISIBLE
                                    }
                                }
                            }
                        }
                    }
                    is FragmentNicknameBinding -> {
                        with(binding) {
                            when (this@setAddTextChangedListener) {
                                nicknameInputText -> {
                                    if (true) {  // 임시 데이터 true 입니당 서버에서 값을 가져와서 확인해야 합니다
                                        setCorrect(nicknameDoneButton, nicknameInputText, null, binding, viewModel, s!!)
                                        binding.nicknameUsedNameNotification.visibility = GONE
                                    } else {
                                        // 서버에 같은 닉네임이 있다면 혹은 닉네임 길이가 0이라면 까지 추가해야합니다.
                                        setIncorrect(
                                            nicknameDoneButton,
                                            nicknameInputText,
                                            null,
                                            resources.getString(R.string.nickname_be_used)
                                        )
                                        binding.nicknameUsedNameNotification.visibility = VISIBLE
                                    }
                                }
                            }
                        }
                    }
                    is FragmentEmailLoginBinding -> {
                        with(binding) {
                            when (this@setAddTextChangedListener) {
                                emailLoginEmail -> {
                                    emailLoginPassword.setBackgroundResource(R.drawable.border_radius_10)
                                    if (s!!.matches(regex!!)) {
                                        // 이메일 형식이 맞는 경우
                                        setCorrect(emailLoginMoveNextButton, emailLoginEmail, null, binding, viewModel, s)
                                        FirstButtonInit.buttonInit(emailLoginMoveNextButton)
                                    } else {
                                        // 이메일 형식이 아닌 경우
                                        setIncorrect(
                                            emailLoginMoveNextButton,
                                            emailLoginEmail,
                                            null,
                                            resources.getString(R.string.email_format_error_text)
                                        )
                                    }
                                }

                                emailLoginPassword -> {
                                    // 8자리 이상인 경우
                                    emailLoginEmail.setBackgroundResource(R.drawable.border_radius_10)
                                    if (s!!.length >= 8) { //  => 로그인 부분은 일단 8글자가 아닌 후에 서버와 통신으로 바꿔야함!
                                        setCorrect(emailLoginMoveNextButton, emailLoginPassword, null, binding, viewModel, s)
                                        // 보통 조건에 부합하는 경우 버튼 클릭 가능하지만 비밀번호는 확인 부분이 있어 한번 더 클릭 못하게 바꿔줍니다
                                        binding.emailLoginMoveNextButton.isClickable = true
                                    }
                                    // 8자보다 짧을 경우
                                    else {
                                        setIncorrect(
                                            emailLoginMoveNextButton,
                                            emailLoginPassword,
                                            null,
                                            resources.getString(R.string.password_length)
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

    private fun <B> setCorrect(
        button: Button,
        emailSubmitEditText: EditText?,
        emailExplainText: TextView?,
        binding: B,
        viewModel: LoginViewModel,
        s: Editable
    ) {
        button.isEnabled = true

        with(emailSubmitEditText) {
            this?.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_correct, 0)
            this?.setBackgroundResource(R.drawable.border_radius_10_purple)
        }

        if (emailExplainText != null) {
            with(emailExplainText) {
                setText(R.string.email_to_certification)
                setTextColor(ContextCompat.getColor(emailExplainText.context, R.color.gray_02))
            }
        }

        with(button) {
            context?.let { ctx ->
                setTextColor(ContextCompat.getColor(ctx, R.color.white))
                background = ContextCompat.getDrawable(ctx, R.drawable.border_radius_08_purple)
                background.setTint(ContextCompat.getColor(ctx, R.color.purple_01))
            }
        }
        when (binding) {
            is FragmentEmailBinding -> viewModel.emailAddress.value = s.toString()
            is FragmentNicknameBinding -> viewModel.nickName.value = s.toString()
            is FragmentPasswordBinding -> {
                if (emailSubmitEditText == binding.passwordEdittext) {
                    viewModel.password.value = s.toString()
                } else if (emailSubmitEditText == binding.passwordEdittextBelow) {
                    viewModel.password.value = s.toString()
                }
            }
        }
    }

    private fun setIncorrect(
        button: Button,
        emailSubmitEditText: EditText?,
        emailExplainText: TextView?,
        errorText: String
    ) {
        button.isEnabled = false

        with(emailSubmitEditText) {
            this?.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_error, 0)
            this?.setBackgroundResource(R.drawable.border_radius_16_red)
        }

        if (emailExplainText != null) {
            with(emailExplainText) {
                text = errorText
                setTextColor(ContextCompat.getColor(emailExplainText.context, R.color.red))
            }
        }

        with(button) {
            context?.let { ctx ->
                setTextColor(ContextCompat.getColor(ctx, R.color.gray_01))
                background.setTint(ContextCompat.getColor(ctx, R.color.gray_02))
            }
        }
    }

}
