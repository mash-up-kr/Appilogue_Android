package com.anonymous.appilogue.features.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentPasswordBinding
import com.anonymous.appilogue.features.base.BaseFragment

class PasswordFragment :
    BaseFragment<FragmentPasswordBinding, LoginViewModel>(R.layout.fragment_password) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListener()
        binding.passwordEdittext.setAddTextChangedListener()
        binding.passwordEdittextBelow.setAddTextChangedListener()
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

    fun EditText.setAddTextChangedListener() {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // do nothing
            }

            override fun afterTextChanged(s: Editable) {
                with(binding) {
                    when (this@setAddTextChangedListener) {
                        passwordEdittext -> {
                            // 아래로 포커스 이동시에 백그라운드 변경 (포커스가 아니라 입력시라는 점은 추후에 수정..)
                            passwordEdittextBelow.setBackgroundResource(R.drawable.border_radius_10)

                            with(binding) {
                                // 8자리 이상인 경우
                                if (s.length >= 8) {
                                    setCorrect(passwordEdittext, s)
                                    // 보통 조건에 부합하는 경우 버튼 클릭 가능하지만 비밀번호는 확인 부분이 있어 한번 더 클릭 못하게 바꿔줍니다
                                    FirstButtonInit.buttonInit(passwordMoveNextButton)
                                    binding.passwordWrongPasswordNotification.visibility = View.GONE
                                }
                                // 8자보다 짧을 경우 => 현재 위에 수정하고 밑에부분에는 추가를 해야만 확인을 함(이거 고쳐)
                                else {
                                    setIncorrect(passwordEdittext, resources.getString(R.string.password_length))
                                    binding.passwordWrongPasswordNotification.visibility = View.VISIBLE
                                }
                            }
                        }

                        passwordEdittextBelow -> {
                            // 위로 포커스 이동시에 백그라운드 변경 (포커스가 아니라 입력시라는 점은 추후에 수정..)
                            passwordEdittext.setBackgroundResource(R.drawable.border_radius_10)

                            with(binding) {
                                // 비밀번호가 같다면
                                if (s.toString() == viewModel.password.value) {
                                    setCorrect(passwordEdittextBelow, s)
                                    binding.passwordWrongPasswordNotification.visibility = View.GONE
                                }
                                // 비밀번호가 다르다면
                                else {
                                    setIncorrect(passwordEdittextBelow, resources.getString(R.string.password_check))
                                    binding.passwordWrongPasswordNotification.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    private fun setCorrect(passwordEditText: EditText, s: Editable) {

        with(binding) {
            passwordMoveNextButton.isEnabled = true

            with(passwordMoveNextButton) {
                context?.let { ctx ->
                    setTextColor(ContextCompat.getColor(ctx, R.color.white))
                    background = ContextCompat.getDrawable(ctx, R.drawable.border_radius_08_purple)
                    background.setTint(ContextCompat.getColor(ctx, R.color.purple_01))
                }
            }
        }
        // 첫번 째 패스워드로 들어왔다면, password 에 저장
        if (passwordEditText == binding.passwordEdittext) {
            viewModel.password.value = s.toString()
        }
        // 두번 째 패스워드로 들어왔다면, checkPassword 에 저장
        else {
            viewModel.checkPassword.value = s.toString()
        }

        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_correct, 0)
        passwordEditText.setBackgroundResource(R.drawable.border_radius_10_purple)
    }

    private fun setIncorrect(passwordEditText: EditText, errorText: String) {
        with(binding) {
            passwordMoveNextButton.isEnabled = false

            // 다를 경우 빨간 글씨로 에러 알려줌
            with(passwordWrongPasswordNotification) {
                text = errorText
                setTextColor(ContextCompat.getColor(passwordWrongPasswordNotification.context, R.color.red))
            }

            with(passwordMoveNextButton) {
                context?.let { ctx ->
                    setTextColor(ContextCompat.getColor(ctx, R.color.gray_01))
                    background.setTint(ContextCompat.getColor(ctx, R.color.gray_02))
                }
            }
        }

        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_error, 0)
        passwordEditText.setBackgroundResource(R.drawable.border_radius_16_red)
    }
}




