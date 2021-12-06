package com.anonymous.appilogue.features.appinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentAppInfoBinding
import com.anonymous.appilogue.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppInfoFragment
    : BaseFragment<FragmentAppInfoBinding, AppInfoViewModel>(R.layout.fragment_app_info) {
    override val viewModel: AppInfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}