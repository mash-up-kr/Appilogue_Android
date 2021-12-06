package com.anonymous.appilogue.features.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentNicknameEditDialogBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NicknameEditFragment :
    BaseFragment<FragmentNicknameEditDialogBinding, MainViewModel>(R.layout.fragment_nickname_edit_dialog) {
    override val viewModel: MainViewModel by activityViewModels()
    private val _profileViewModel: ProfileViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            mainViewModel = viewModel
            profileViewModel = _profileViewModel
        }
        with(binding) {
            tvCancel.setOnClickListener {
                parentFragmentManager.commit {
                    remove(this@NicknameEditFragment)
                }
            }
            tvComplete.setOnClickListener {
                saveNickname()
                parentFragmentManager.commit {
                    remove(this@NicknameEditFragment)
                }
            }
        }
    }

    fun saveNickname() {
        viewModel.myUser.value?.let { user ->
            _profileViewModel.saveNickname(user)
            _profileViewModel.nickname.value?.let { nickname ->
                viewModel.editNickname(
                    nickname = nickname
                )
            }
        }
    }
}