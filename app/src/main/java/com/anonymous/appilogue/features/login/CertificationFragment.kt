package com.anonymous.appilogue.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentCertificationBinding
import com.anonymous.appilogue.features.base.BaseFragment

class CertificationFragment :
    BaseFragment<FragmentCertificationBinding, LoginViewModel>(R.layout.fragment_certification) {
    override val viewModel = LoginViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_certification, container, false)
    }

}