package com.anonymous.appilogue.features.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.anonymous.appilogue.AppilogueApplication
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentEmailLoginBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity
import com.anonymous.appilogue.persistence.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class LoginEmailFragment :
    BaseFragment<FragmentEmailLoginBinding, LoginViewModel>(R.layout.fragment_email_login) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emailCheckRegex = "\\w+@\\w+.(com|net|COM|NET)".toRegex()

        initView()
        setAddTextChangedListener(emailCheckRegex)
    }

    private fun initView() {
        FirstButtonInit.buttonInit(binding.emailLoginMoveNextButton)

        binding.emailLoginBackButton.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.emailLoginMoveNextButton.setOnClickListener {
            Timber.d("${viewModel.emailAddress.value.toString()}, ${viewModel.password.value.toString()}")
            viewModel.loginWithEmailPassword()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.accessToken.isEmpty()) {
                        Timber.d("엑세스 토큰 없어..")
                        setIncorrect(binding.emailLoginEmail)
                        setIncorrect(binding.emailLoginPassword)
                    } else {
                        // accessToken 값 sharedPreference 에 추가
                        PreferencesManager.saveToken(it.accessToken)
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                }) {
                    setIncorrect(binding.emailLoginEmail)
                    setIncorrect(binding.emailLoginPassword)
                    Timber.d("${it.message} 오류 존재!")
                    // 혹시 아이디 비번 틀렸다고 알려준다면 여기서 수정
                }
        }

        binding.emailLoginForgetId.setOnClickListener {
            viewModel.lostPassword = true
            it.findNavController().navigate(R.id.action_loginEmailFragment_to_emailFragment)
        }
    }

    private fun setAddTextChangedListener(emailCheckRegex: Regex) {
        binding.emailLoginEmail.addTextChangedListener {
            with(binding) {
                // 8자리 이상인 경우
                if (emailCheckRegex.matches(it.toString())) {
                    setCorrect(emailLoginEmail, it as Editable)
                    // 보통 조건에 부합하는 경우 버튼 클릭 가능하지만 비밀번호는 확인 부분이 있어 한번 더 클릭 못하게 바꿔줍니다
                    FirstButtonInit.buttonInit(emailLoginMoveNextButton)
                }
                // 8자보다 짧을 경우 => 현재 위에 수정하고 밑에부분에는 추가를 해야만 확인을 함(이거 고쳐)
                else {
                    setIncorrect(emailLoginEmail)
                }
            }
        }
        binding.emailLoginPassword.addTextChangedListener {
            with(binding) {
                // 비밀번호 형식(8글자 이상 맞다면)
                if (it.toString().length >= 8) {
                    setCorrect(emailLoginPassword, it as Editable)
                } else {
                    setIncorrect(emailLoginPassword)
                }
            }
        }

    }


    private fun setCorrect(emailOrPassword: EditText, correctEmailOrPassword: Editable) {

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
        if (emailOrPassword == binding.emailLoginEmail) {
            viewModel.emailAddress.value = correctEmailOrPassword.toString()
        }
        // 두번 째 패스워드로 들어왔다면, checkPassword 에 저장
        else {
            viewModel.password.value = correctEmailOrPassword.toString()
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
