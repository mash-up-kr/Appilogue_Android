package com.anonymous.appilogue.features.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentEmailLoginBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity

class LoginEmailFragment :
    BaseFragment<FragmentEmailLoginBinding, LoginViewModel>(R.layout.fragment_email_login) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emailCheckRegex = "\\w+@\\w+.(com|net|COM|NET)".toRegex()

        initView()

        binding.emailLoginEmail.setAddTextChangedListener(emailCheckRegex)
        binding.emailLoginPassword.setAddTextChangedListener()

    }

    private fun initView() {
        binding.emailLoginMoveNextButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.emailLoginForgetId.setOnClickListener {
            viewModel.lostPassword = true
            it.findNavController().navigate(R.id.action_loginEmailFragment_to_emailFragment)
        }
    }

    fun EditText.setAddTextChangedListener(emailCheckRegex: Regex? = null) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                with(binding) {
                    when (this@setAddTextChangedListener) {
                        emailLoginEmail -> {
                            // 아래로 포커스 이동시에 백그라운드 변경 (포커스가 아니라 입력시라는 점은 추후에 수정..)
                            emailLoginPassword.setBackgroundResource(R.drawable.border_radius_10)

                            with(binding) {
                                // 8자리 이상인 경우
                                if (s!!.matches(emailCheckRegex!!)) {
                                    setCorrect(emailLoginEmail, s)
                                    // 보통 조건에 부합하는 경우 버튼 클릭 가능하지만 비밀번호는 확인 부분이 있어 한번 더 클릭 못하게 바꿔줍니다
                                    FirstButtonInit.buttonInit(emailLoginMoveNextButton)
                                }
                                // 8자보다 짧을 경우 => 현재 위에 수정하고 밑에부분에는 추가를 해야만 확인을 함(이거 고쳐)
                                else {
                                    setIncorrect(emailLoginEmail)
                                }
                            }
                        }

                        emailLoginPassword -> {
                            // 위로 포커스 이동시에 백그라운드 변경 (포커스가 아니라 입력시라는 점은 추후에 수정..)
                            emailLoginEmail.setBackgroundResource(R.drawable.border_radius_10)

                            with(binding) {
                                // 비밀번호가 같다면 (서버랑 연동하는 부분으로 수정해야함)
                                if (s!!.toString() == viewModel?.password?.value.toString()) {
                                    setCorrect(emailLoginPassword, s)
                                }
                                // 비밀번호가 다르다면
                                else {
                                    setIncorrect(emailLoginPassword)
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    private fun setCorrect(emailOrPassword: EditText, s: Editable) {

        with(binding) {
            emailLoginMoveNextButton.isEnabled = true

            with(emailLoginMoveNextButton) {
                context?.let { ctx ->
                    setTextColor(ContextCompat.getColor(ctx, R.color.white))
                    background = ContextCompat.getDrawable(ctx, R.drawable.border_radius_08_purple)
                    background.setTint(ContextCompat.getColor(ctx, R.color.purple_01))
                }
            }
        }
        // 첫번 째 이메일로 들어왔다면, 서버 이메일과 비교, 여기서는 임시로 뷰모델 데이터랑 비교만
        if (emailOrPassword == viewModel.emailAddress) {
            viewModel.password.value = s.toString()
        }
        // 두번 째 패스워드로 들어왔다면, checkPassword 에 저장
        else {
            viewModel.checkPassword.value = s.toString()
        }

        emailOrPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_correct, 0)
        emailOrPassword.setBackgroundResource(R.drawable.border_radius_10_purple)
    }

    private fun setIncorrect(emailOrPassword: EditText) {
        with(binding) {
            emailLoginMoveNextButton.isEnabled = false

            with(emailLoginMoveNextButton) {
                context?.let { ctx ->
                    setTextColor(ContextCompat.getColor(ctx, R.color.gray_01))
                    background.setTint(ContextCompat.getColor(ctx, R.color.gray_02))
                }
            }
        }

        emailOrPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_error, 0)
        emailOrPassword.setBackgroundResource(R.drawable.border_radius_16_red)
    }
}
