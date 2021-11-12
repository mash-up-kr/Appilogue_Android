package com.anonymous.appilogue.features.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentNicknameBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity

class NicknameFragment :
    BaseFragment<FragmentNicknameBinding, LoginViewModel>(R.layout.fragment_nickname) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListener()
        binding.nicknameInputText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.nicknameCounting.text = "${binding.nicknameInputText.length()}/10"
            }

            override fun afterTextChanged(s: Editable?) {
                with(binding) {
                    if (true) {  // 임시 데이터 true 입니당 서버에서 값을 가져와서 확인해야 합니다
                        setCorrect(s!!)
                        nicknameUsedNameNotification.visibility = GONE
                    } else if (s.isNullOrEmpty()) {
                        nicknameUsedNameNotification.text = getString(R.string.please_enter_nickname)
                        nicknameDoneButton.isEnabled
                    } else {
                        // 서버에 같은 닉네임이 있다면.
                        setIncorrect()
                        nicknameUsedNameNotification.visibility = View.VISIBLE
                    }
                }

            }
        })
    }

    private fun initClickListener() {
        with(binding) {
            with(nicknameDoneButton) {
                binding.nicknameUsedNameNotification.visibility = GONE
                FirstButtonInit.buttonInit(this)
                setOnClickListener {
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setCorrect(
        s: Editable
    ) {
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

}
