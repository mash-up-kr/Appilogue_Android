package com.anonymous.appilogue.features.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.anonymous.appilogue.AppilogueApplication
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentLoginBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(R.layout.fragment_login) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        viewModel.lostPassword = false
    }

    private fun initView() {
        binding.btSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_emailFragment)
        }
        binding.btEmailLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_loginEmailFragment)
        }
        binding.btKakaoLogin.setOnClickListener {
            kakaoLogin()
        }
    }

    private fun kakaoLoginCallback(token: OAuthToken?, error: Throwable?) {
        Timber.d("${token?.accessToken}")
        if (error != null) {
            when {
                error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                    Timber.d("접근이 거부 됨(동의 취소)")
                }
                error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                    Timber.d("유효하지 않은 앱")
                }
                error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                    Timber.d("인증 수단이 유효하지 않아 인증할 수 없는 ")
                }
                error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                    Timber.d("요청 파라미터 오류")
                }
                error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                    Timber.d("유효하지 않은 scope ID")
                }
                error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                    Timber.d("설정이 올바르지 않음(android key hash")
                }
                error.toString() == AuthErrorCause.ServerError.toString() -> {
                    Timber.d("서버 내부 에러")
                }
                error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                    Timber.d("앱이 요청 권한이 없음")
                }
                else -> { // Unknown
                    Timber.d("기타 에러")
                }
            }
        } else if (token != null) {
            AppilogueApplication.prefs.myEditText = token.accessToken
            Toast.makeText(requireContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun kakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = ::kakaoLoginCallback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = ::kakaoLoginCallback)
        }
    }
}

