package com.anonymous.appilogue.features.home.bottomsheet.space_dust

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentSaveSpaceDustDialogBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.home.HomeViewModel
import com.anonymous.appilogue.features.main.MainViewModel

class SaveSpaceDustDialogFragment :
    BaseFragment<FragmentSaveSpaceDustDialogBinding, HomeViewModel>(
        R.layout.fragment_save_space_dust_dialog
    ) {
    override val viewModel: HomeViewModel by activityViewModels()
    val mainViewModel: MainViewModel by activityViewModels()
    val mySpaceDustViewModel: MySpaceDustViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            ivCross.setOnClickListener {
                parentFragmentManager.commit {
                    remove(this@SaveSpaceDustDialogFragment)
                }
            }
            tvCheck.setOnClickListener {
                mainViewModel.myUser.value?.let { user ->
                    mySpaceDustViewModel.saveMySpaceDust(user)
                }
                viewModel.showSaveSuccessToast()
                parentFragmentManager.commit {
                    remove(this@SaveSpaceDustDialogFragment)
                }
            }
        }
    }
}