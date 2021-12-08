package com.anonymous.appilogue.features.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentProfileSettingBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.login.LoginActivity
import com.anonymous.appilogue.features.main.MainActivity
import com.anonymous.appilogue.features.main.MainViewModel
import com.anonymous.appilogue.persistence.PreferencesManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileSettingFragment :
    BaseFragment<FragmentProfileSettingBinding, MainViewModel>(R.layout.fragment_profile_setting) {
    override val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            mainViewModel = viewModel
        }
        with(binding) {
            ivBack.setOnClickListener {
                (activity as MainActivity).onBackPressed()
            }
            tvLogout.setOnClickListener {
                showLogoutDialogue()
            }
            tvServiceResign.setOnClickListener {
                showWithdrawDialogue()
            }
        }
    }

    private fun showLogoutDialogue() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.logout_alert_title)
            .setPositiveButton(R.string.ok_text) { dialog, _ ->
                doLogout()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showWithdrawDialogue() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.withdraw_alert_title)
            .setPositiveButton(R.string.ok_text) { dialog, _ ->
                viewModel.deleteMyAccount()
                doLogout()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun doLogout() {
        PreferencesManager.logout()
        Intent(activity, LoginActivity::class.java).run {
            startActivity(this)
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        (activity as MainActivity).showBottomNavigation()
        super.onDestroyView()
    }
}