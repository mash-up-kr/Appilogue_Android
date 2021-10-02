package com.anonymous.appilogue.features.review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentReviewSelectorBinding
import com.anonymous.appilogue.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewSelectorFragment
    : BaseFragment<FragmentReviewSelectorBinding, ReviewSelectorViewModel>(R.layout.fragment_review_selector) {

    override val viewModel: ReviewSelectorViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObservers()
    }

    private fun initView() {
        binding.blackHoleView.setOnClickListener {
            viewModel.isBlackHoleSelected
        }

        binding.whiteHoleView.setOnClickListener {
            it.isSelected = true
            binding.blackHoleView.isSelected = false
        }
    }

    private fun initObservers() {
        viewModel.isBlackHoleSelected.observe(viewLifecycleOwner) {
            viewModel.selectBlackHole()
        }
        viewModel.isWhiteHoleSelected.observe(viewLifecycleOwner) {
            viewModel.selectWhiteHole()
        }
    }

    companion object {
        const val SELECT_APP_KEY = "REVIEW_APP_KEY"

        fun newInstance() {
            val instance = ReviewSelectorFragment()

        }
    }
}