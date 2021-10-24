package com.anonymous.appilogue.features.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentNotificationBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment :
    BaseFragment<FragmentNotificationBinding, NotificationViewModel>(R.layout.fragment_notification) {
    override val viewModel: NotificationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            notificationViewModel = viewModel
        }
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).navigateTo(R.id.homeFragment)
        }
        binding.rvNotification.adapter = NotificationAdapter()
        viewModel.fetchNotifications()
    }
}