package com.anonymous.appilogue.features.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentNicknameBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.login.SettingTextChanger.setAddTextChangedListener
import com.anonymous.appilogue.features.main.MainActivity

class NicknameFragment :
    BaseFragment<FragmentNicknameBinding, LoginViewModel>(R.layout.fragment_nickname) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            nicknameInputText.setAddTextChangedListener(binding, viewModel)
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
}
