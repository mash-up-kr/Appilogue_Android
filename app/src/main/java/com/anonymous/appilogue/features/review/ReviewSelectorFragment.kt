package com.anonymous.appilogue.features.review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentReviewSelectorBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewSelectorFragment
    : BaseFragment<FragmentReviewSelectorBinding, ReviewSelectorViewModel>(R.layout.fragment_review_selector) {

    override val viewModel: ReviewSelectorViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            mainVm = (activity as MainActivity).mainViewModel
        }

        initView()
    }

    private fun initView() {
        binding.blackHoleView.setOnClickListener {
            it.isSelected = true
            binding.whiteHoleView.isSelected = false
            binding.planetDescriptionView.apply {
                visibility = View.VISIBLE
                text = getString(R.string.description_black_hole)
            }
            binding.selectButton.apply {
                setBackgroundColor(resources.getColor(R.color.purple_01, context.theme))
                setTextColor(resources.getColor(R.color.white, context.theme))
            }
        }

        binding.whiteHoleView.setOnClickListener {
            it.isSelected = true
            binding.blackHoleView.isSelected = false
            viewModel.selectWhiteHole()
            binding.planetDescriptionView.apply {
                visibility = View.VISIBLE
                text = getString(R.string.description_white_hole)
            }
            binding.selectButton.apply {
                setBackgroundColor(resources.getColor(R.color.purple_01, context.theme))
                setTextColor(resources.getColor(R.color.white, context.theme))
            }
        }
    }
}