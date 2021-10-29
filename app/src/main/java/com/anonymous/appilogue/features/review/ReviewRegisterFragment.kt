package com.anonymous.appilogue.features.review

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentReviewRegisterBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity
import com.anonymous.appilogue.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ReviewRegisterFragment
    : BaseFragment<FragmentReviewRegisterBinding, ReviewRegisterViewModel>(R.layout.fragment_review_register) {

    override val viewModel: ReviewRegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind {
            vm = viewModel
            mainVm = (activity as MainActivity).viewModel
        }

        initView()
    }

    private fun initView() {
        binding.selectButton.setOnClickListener {
            context?.showToast("리뷰 등록 완료") // 임시 조치
        }
    }
}