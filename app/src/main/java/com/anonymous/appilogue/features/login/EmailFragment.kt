package com.anonymous.appilogue.features.login

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentEmailBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class EmailFragment :
    BaseFragment<FragmentEmailBinding, LoginViewModel>(R.layout.fragment_email) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailCheckRegex = "\\w+@\\w+.(com|net|COM|NET)".toRegex()

        setDifferentTextIfLostPassword()
        initClickListener()
        setTextChangeListener(emailCheckRegex)
    }

    // 비밀 번호 찾기를 눌렀을 때와 회원 가입을 눌렀을 때 상단의 텍스트 변경
    private fun setDifferentTextIfLostPassword() {
        if (viewModel.lostPassword) {
            binding.emailEnterEmailText.text = getString(R.string.email_signed_up_text)
        } else {
            binding.emailEnterEmailText.text = getString(R.string.insert_email)
        }
    }

    private fun initClickListener() {
        with(binding) {
            with(emailMoveNextButton) {
                FirstButtonInit.buttonInit(this)

                clicks()
                    .throttleFirst(3000L, TimeUnit.MILLISECONDS)
                    .subscribe {
                        viewModel.sendCertificationNumber(viewModel.lostPassword)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ sendEmail ->
                                if (sendEmail.isSend) {
                                    viewModel.timerStart()
                                    binding.emailMoveNextButton.findNavController().navigate(R.id.action_emailFragment_to_certificationFragment)
                                } else {
                                    setIncorrect(resources.getString(R.string.alreay_signup))
                                }

                            }, {
                                Timber.d("${it.message} ")
                                setIncorrect(resources.getString(R.string.alreay_signup))
                            })
                    }
            }
        }
    }

    private fun setTextChangeListener(emailCheckRegex: Regex) {
        binding.emailSubmitEditText.doAfterTextChanged { email ->
            if (email?.matches(emailCheckRegex) == true) {
                // 이메일 형식이 맞는 경우
                setCorrect(email.toString())
            } else {
                // 이메일 형식이 아닌 경우
                setIncorrect(resources.getString(R.string.email_format_error_text))
            }
        }
    }

    private fun setCorrect(correctEmailText: String?) {
        with(binding) {
            setButtonCanClick(true)
            changeEditTextBackgroundColor(true)

            with(emailExplainText) {
                setText(R.string.email_to_certification)
                setTextColor(ContextCompat.getColor(emailExplainText.context, R.color.gray_02))
            }
            viewModel.emailAddress.value = correctEmailText
        }
    }

    private fun setIncorrect(errorText: String) {
        with(binding) {
            setButtonCanClick(false)
            changeEditTextBackgroundColor(false)

            with(emailExplainText) {
                text = errorText
                setTextColor(ContextCompat.getColor(emailExplainText.context, R.color.red))
            }
        }
    }

    private fun setButtonBackgroundColor(click: Boolean) {
        if (click) {
            with(binding.emailMoveNextButton) {
                context?.let { ctx ->
                    setTextColor(ContextCompat.getColor(ctx, R.color.white))
                    background = ContextCompat.getDrawable(ctx, R.drawable.border_radius_08_purple)
                    background.setTint(ContextCompat.getColor(ctx, R.color.purple_01))
                }
            }
        } else {
            with(binding.emailMoveNextButton) {
                context?.let { ctx ->
                    setTextColor(ContextCompat.getColor(ctx, R.color.gray_01))
                    background.setTint(ContextCompat.getColor(ctx, R.color.gray_02))
                }
            }
        }
    }

    private fun setButtonCanClick(click: Boolean) {
        if (click) {
            with(binding.emailMoveNextButton) {
                setButtonBackgroundColor(true)
                isEnabled = true
            }
        } else {
            with(binding.emailMoveNextButton) {
                setButtonBackgroundColor(false)
                isEnabled = false
            }
        }
    }

    private fun changeEditTextBackgroundColor(change: Boolean) {
        if (change) {
            binding.emailSubmitEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_correct, 0)
            binding.emailSubmitEditText.setBackgroundResource(R.drawable.border_radius_10_purple)
        } else {
            binding.emailSubmitEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_error, 0)
            binding.emailSubmitEditText.setBackgroundResource(R.drawable.border_radius_16_red)
        }
    }
}
