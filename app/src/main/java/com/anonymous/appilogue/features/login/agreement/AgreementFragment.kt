package com.anonymous.appilogue.features.login.agreement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentAgreementBinding
import com.anonymous.appilogue.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgreementFragment : BaseFragment<FragmentAgreementBinding, AgreementViewModel>(R.layout.fragment_agreement) {

    override val viewModel: AgreementViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind {
            vm = viewModel
        }

        initView()
    }

    private fun initView() {
        bind {
            toolbarLeftIconView.setOnClickListener {
                activity?.onBackPressed()
            }
            toolbarRightIconView.setOnClickListener {
                activity?.onBackPressed()
            }
            termsReadMoreView.setOnClickListener {
                val action = AgreementFragmentDirections.actionAgreementFragmentToAgreementDetailFragment(AgreementDetailViewModel.TERMS)
                findNavController().navigate(action)
            }
            personalReadMoreView.setOnClickListener {
                val action = AgreementFragmentDirections.actionAgreementFragmentToAgreementDetailFragment(AgreementDetailViewModel.PERSONAL_INFO)
                findNavController().navigate(action)
            }
            nextButtonView.setOnClickListener {
                if (viewModel.areAllChecked.value) {
                    val action =
                        AgreementFragmentDirections.actionAgreementFragmentToEmailFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }
}