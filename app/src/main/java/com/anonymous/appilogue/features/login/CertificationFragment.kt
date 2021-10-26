package com.anonymous.appilogue.features.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentCertificationBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity

class CertificationFragment :
    BaseFragment<FragmentCertificationBinding, LoginViewModel>(R.layout.fragment_certification) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        with(binding) {
            with(certificationMoveNextButton) {
                isEnabled = false
                context?.let { ctx ->
                    setTextColor(ContextCompat.getColor(ctx, R.color.gray_01))
                    setBackgroundColor(ContextCompat.getColor(ctx, R.color.black_01))
                }
            }

            certificationNumber1.requestFocus()
            setAddTextChangeListener()          // 포커스 자동 넘김

            certificationMoveNextButton.setOnClickListener {
                viewModel?.stopTimer()
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

    private fun FragmentCertificationBinding.setAddTextChangeListener() {
        val certificationNumberList =
            listOf(certificationNumber1, certificationNumber2, certificationNumber3, certificationNumber4, certificationNumber5, certificationNumber6)

        for (index in 0..4) {
            certificationNumberList[index].addTextChangedListener {
                if (it!!.isNotEmpty()) {
                    certificationNumberList[index + 1].requestFocus()
                }
            }
        }
        certificationNumberList[5].addTextChangedListener {
            if (it!!.isNotEmpty()) {
                with(certificationMoveNextButton) {
                    isEnabled = true
                    context?.let { ctx ->
                        setTextColor(ContextCompat.getColor(ctx, R.color.white))
                        setBackgroundColor(ContextCompat.getColor(ctx, R.color.purple_01))
                    }
                }
            } else {
                with(certificationMoveNextButton) {
                    isEnabled = false
                    context?.let { ctx ->
                        setTextColor(ContextCompat.getColor(ctx, R.color.gray_01))
                        setBackgroundColor(ContextCompat.getColor(ctx, R.color.black_01))
                    }
                }
            }
        }
    }
}
