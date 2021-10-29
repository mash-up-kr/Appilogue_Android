package com.anonymous.appilogue.features.review

import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
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

        bind {
            vm = viewModel
            mainVm = (activity as MainActivity).viewModel
        }

        initView()
    }

    private fun initView() {
        binding.blackHoleView.setOnClickListener {
            it.isSelected = true
            binding.whiteHoleView.isSelected = false
            viewModel.selectBlackHole()
            binding.planetDescriptionView.apply {
                handleSelectEvent(R.string.description_black_hole, R.string.black_hole_text, R.color.mint)
            }
        }

        binding.whiteHoleView.setOnClickListener {
            it.isSelected = true
            binding.blackHoleView.isSelected = false
            viewModel.selectWhiteHole()
            binding.planetDescriptionView.apply {
                handleSelectEvent(R.string.description_white_hole, R.string.white_hole_text, R.color.mint)
            }
        }
        binding.selectButton.setOnClickListener {
            if (viewModel.isSelected()) {
                val action = ReviewSelectorFragmentDirections.actionReviewSelectorFragmentToReviewRegisterFragment(viewModel.isBlackHoleSelected.value)
                (activity as MainActivity).navigateTo(action)
            }
        }
    }

    private fun TextView.handleSelectEvent(
        contentTextId: Int,
        holeTextId: Int,
        spanColorId: Int
    ) {
        visibility = View.VISIBLE
        text = getString(contentTextId)

        val holeText = getString(holeTextId)
        val spanColor = context.getColor(spanColorId)
        val span = text as Spannable
        span.setSpan(
            ForegroundColorSpan(spanColor),
            text.indexOf(holeText),
            text.indexOf(holeText) + holeText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}