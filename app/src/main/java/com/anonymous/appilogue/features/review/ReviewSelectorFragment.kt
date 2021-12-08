package com.anonymous.appilogue.features.review

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentReviewSelectorBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity
import com.anonymous.appilogue.utils.handleSelectEvent
import com.anonymous.appilogue.utils.hideKeyboardDown
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewSelectorFragment
    : BaseFragment<FragmentReviewSelectorBinding, ReviewSelectorViewModel>(R.layout.fragment_review_selector) {

    override val viewModel: ReviewSelectorViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.hideKeyboardDown()

        bind {
            vm = viewModel
        }

        initView()
    }

    private fun initView() {
        bind {
            blackHoleView.setOnClickListener {
                it.isSelected = true
                binding.whiteHoleView.isSelected = false
                viewModel.selectBlackHole()
                binding.planetDescriptionView.apply {
                    handleSelectEvent(R.string.description_black_hole, R.string.black_hole_text, R.color.mint_01)
                }
            }

            whiteHoleView.setOnClickListener {
                it.isSelected = true
                binding.blackHoleView.isSelected = false
                viewModel.selectWhiteHole()
                binding.planetDescriptionView.apply {
                    handleSelectEvent(R.string.description_white_hole, R.string.white_hole_text, R.color.mint_01)
                }
            }

            selectButton.setOnClickListener {
                if (viewModel.isSelected()) {
                    val action = ReviewSelectorFragmentDirections.actionReviewSelectorFragmentToReviewRegisterFragment(viewModel.appName, viewModel.appIconUrl, viewModel.isBlackHoleSelected.value)
                    (activity as MainActivity).navigateTo(action)
                }
            }

            toolbarLeftIconView.setOnClickListener {
                activity?.onBackPressed()
            }

            toolbarRightIconView.setOnClickListener {
                val action = ReviewSelectorFragmentDirections.actionReviewSelectorFragmentToHomeFragment()
                (activity as MainActivity).navigateTo(action)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).hideBottomNavigation()
    }
}