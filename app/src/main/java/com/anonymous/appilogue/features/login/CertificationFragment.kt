package com.anonymous.appilogue.features.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentCertificationBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            certificationNumber1.requestFocus()

            certificationMoveNextButton.setOnClickListener {
                // 서버의 인증 값과 동일하다면
//                it.findNavController()
//                    .navigate(R.id.action_certificationFragment_to_passwordFragment)
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
            certificationBackButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_certificationFragment_to_emailFragment)
            }
            certificationCloseButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_certificationFragment_to_loginFragment)
            }

        }

    }

}