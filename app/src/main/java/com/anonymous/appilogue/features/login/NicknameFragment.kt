package com.anonymous.appilogue.features.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.View.GONE
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentNicknameBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class NicknameFragment :
    BaseFragment<FragmentNicknameBinding, LoginViewModel>(R.layout.fragment_nickname) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDoneClickListener()
        addTextChangeListenerNicknameInputText()
    }

    private fun addTextChangeListenerNicknameInputText() {
        binding.nicknameInputText.doOnTextChanged { _, _, _, _ ->
            binding.nicknameCounting.text = "${binding.nicknameInputText.length()}/10"
        }

        binding.nicknameInputText.textChanges()
            .debounce(1000L, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.validateNickname(it.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ validateNickName ->
                        when {
                            it.toString() == "" -> {
                                setInitState()
                                with(binding.nicknameUsedNameNotification) {
                                    text = getString(R.string.please_enter_nickname)
                                    visibility = View.VISIBLE
                                    context?.let { ctx ->
                                        setTextColor(ContextCompat.getColor(ctx, R.color.gray_02))
                                    }
                                }
                            }
                            validateNickName.isUnique -> {
                                setCorrect(it as Editable)
                                binding.nicknameUsedNameNotification.visibility = GONE
                            }
                            else -> {
                                setIncorrect()
                                with(binding.nicknameUsedNameNotification) {
                                    text = getString(R.string.nickname_be_used)
                                    context?.let { ctx ->
                                        setTextColor(ContextCompat.getColor(ctx, R.color.red))
                                    }
                                }
                                binding.nicknameUsedNameNotification.visibility = View.VISIBLE
                            }
                        }
                    }) {
                        Timber.d("${it.message}")
                    }

            }
    }

    private fun initDoneClickListener() {
        with(binding) {
            with(nicknameDoneButton) {
                binding.nicknameUsedNameNotification.visibility = GONE
                FirstButtonInit.buttonInit(this)
                setOnClickListener {
                    sendToServerUserData()
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun sendToServerUserData() {
        viewModel.sendToServerUserData().subscribe({
            if (it.isVerified) {
                Timber.d("등록 성공")
            } else {
                Timber.d("등록 오류")
            }
        }) {
            Timber.d("${it.message.toString()}")
        }
    }

    private fun setCorrect(s: Editable) {
        with(binding) {
            nicknameDoneButton.isEnabled = true

            with(nicknameInputText) {
                setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_correct, 0)
                setBackgroundResource(R.drawable.border_radius_10_purple)
            }

            with(nicknameDoneButton) {
                context?.let { ctx ->
                    setTextColor(ContextCompat.getColor(ctx, R.color.white))
                    background = ContextCompat.getDrawable(ctx, R.drawable.border_radius_08_purple)
                    background.setTint(ContextCompat.getColor(ctx, R.color.purple_01))
                }
            }
        }

        viewModel.nickName.value = s.toString()
    }

    private fun setIncorrect() {
        with(binding) {
            nicknameDoneButton.isEnabled = false

            with(nicknameInputText) {
                setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_error, 0)
                setBackgroundResource(R.drawable.border_radius_16_red)
            }

            with(nicknameDoneButton) {
                context?.let { ctx ->
                    setTextColor(ContextCompat.getColor(ctx, R.color.gray_01))
                    background.setTint(ContextCompat.getColor(ctx, R.color.gray_02))
                }
            }
        }
    }

    private fun setInitState() {
        with(binding) {
            nicknameDoneButton.isEnabled = false

            with(nicknameInputText) {
                setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                setBackgroundResource(R.drawable.border_radius_10)
            }

            with(nicknameDoneButton) {
                context?.let { ctx ->
                    setTextColor(ContextCompat.getColor(ctx, R.color.gray_01))
                    background.setTint(ContextCompat.getColor(ctx, R.color.gray_02))
                }
            }
        }
    }
}
