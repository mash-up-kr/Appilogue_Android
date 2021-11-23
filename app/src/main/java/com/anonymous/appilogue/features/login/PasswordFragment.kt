package com.anonymous.appilogue.features.login

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentPasswordBinding
import com.anonymous.appilogue.features.base.BaseFragment
import org.mindrot.jbcrypt.BCrypt

class PasswordFragment :
    BaseFragment<FragmentPasswordBinding, LoginViewModel>(R.layout.fragment_password) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListener()
        setAddTextChangedListener()
    }

    private fun initClickListener() {
        with(binding) {
            with(passwordMoveNextButton) {
                FirstButtonInit.buttonInit(this)
                binding.passwordWrongPasswordNotification.visibility = View.GONE

                // 비밀번호 찾기를 통해 넘어오면 다음 페이지가 로그인 화면, 회원 가입의 경우 닉네임 설정으로 이동
                if (viewModel.lostPassword) {
                    binding.passwordEnterPasswordText.text = getString(R.string.password_reset)
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
        }
    }

    private fun setAddTextChangedListener() {
        binding.passwordEdittext.doAfterTextChanged { passwordText ->
            passwordText?.let {
                if (passwordText.length >= 8) {
                    setCorrect(binding.passwordEdittext, it.toString())
                    binding.passwordWrongPasswordNotification.visibility = View.GONE
                    if (binding.passwordEdittextBelow.text.isNotEmpty()) {
                        checkBetweenPassword()
                    }
                } else {
                    setIncorrect(binding.passwordEdittext, resources.getString(R.string.password_length))
                    binding.passwordWrongPasswordNotification.visibility = View.VISIBLE
                }
            }
        }

        binding.passwordEdittextBelow.doAfterTextChanged { passwordBelowText ->
            passwordBelowText?.let {
                if (checkBetweenPassword()) {
                    setCorrect(binding.passwordEdittextBelow, it.toString())
                    binding.passwordWrongPasswordNotification.visibility = View.GONE
                } else {
                    setIncorrect(binding.passwordEdittextBelow, resources.getString(R.string.password_check))
                    binding.passwordWrongPasswordNotification.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun setCorrect(passwordEditText: EditText, correctPassword: String) {
        // 첫번 째 패스워드로 들어왔다면, password 에 저장
        if (passwordEditText == binding.passwordEdittext) {
            viewModel.password.value = correctPassword
        }
        // 두번 째 패스워드로 들어왔다면, checkPassword 에 저장
        else {
            val passwordHashed = BCrypt.hashpw(correctPassword, BCrypt.gensalt())
            viewModel.checkPassword.value = passwordHashed
        }
        changeEditTextBackgroundColor(passwordEditText, true)
    }

    private fun setIncorrect(passwordEditText: EditText, errorText: String) {
        binding.passwordWrongPasswordNotification.text = errorText
        changeEditTextBackgroundColor(passwordEditText, false)
    }

    private fun checkBetweenPassword(): Boolean {
        return if (binding.passwordEdittext.text.toString() == binding.passwordEdittextBelow.text.toString()) {
            setButtonCanClick(true)
            changeEditTextBackgroundColor(binding.passwordEdittextBelow, true)
            true
        } else {
            with(binding) {
                // 위와 아래 패스워드가 올바르게 입력된 상태에서 위의 패스워드를 추가하면,
                // 길이는 더 길어지고 형식에는 맞지만, 위와 아래의 값은 달라지므로 아래의 패스워드 백그라운드를 변경해줍니다.
                if (passwordEdittext.text.length >= 8) {
                    changeEditTextBackgroundColor(passwordEdittext, true)
                    changeEditTextBackgroundColor(passwordEdittextBelow, false)
                }
                // 다를 경우 빨간 글씨로 에러 알려줌
                with(passwordWrongPasswordNotification) {
                    setTextColor(ContextCompat.getColor(passwordWrongPasswordNotification.context, R.color.red))
                }
                setButtonCanClick(false)
            }
            false
        }
    }

    private fun setButtonBackgroundColor(click: Boolean) {
        if (click) {
            with(binding.passwordMoveNextButton) {
                context?.let { ctx ->
                    setTextColor(ContextCompat.getColor(ctx, R.color.white))
                    background = ContextCompat.getDrawable(ctx, R.drawable.border_radius_08_purple)
                    background.setTint(ContextCompat.getColor(ctx, R.color.purple_01))
                }
            }
        } else {
            with(binding.passwordMoveNextButton) {
                context?.let { ctx ->
                    setTextColor(ContextCompat.getColor(ctx, R.color.gray_01))
                    background.setTint(ContextCompat.getColor(ctx, R.color.gray_02))
                }
            }
        }
    }

    private fun setButtonCanClick(click: Boolean) {
        if (click) {
            with(binding.passwordMoveNextButton) {
                setButtonBackgroundColor(true)
                isEnabled = true
            }
        } else {
            with(binding.passwordMoveNextButton) {
                setButtonBackgroundColor(false)
                isEnabled = false
            }
        }
    }

    private fun changeEditTextBackgroundColor(passwordEditText: EditText, change: Boolean) {
        if (change) {
            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_correct, 0)
            passwordEditText.setBackgroundResource(R.drawable.border_radius_10_purple)
        } else {
            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_error, 0)
            passwordEditText.setBackgroundResource(R.drawable.border_radius_16_red)
        }
    }
}




