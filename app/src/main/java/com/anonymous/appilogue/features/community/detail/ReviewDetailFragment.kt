package com.anonymous.appilogue.features.community.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentReviewDetailBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.features.base.isSuccessful
import com.anonymous.appilogue.features.main.MainActivity
import com.anonymous.appilogue.model.ReviewInfo
import com.anonymous.appilogue.utils.hideKeyboardDown
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReviewDetailFragment
    : BaseFragment<FragmentReviewDetailBinding, ReviewDetailViewModel>(R.layout.fragment_review_detail){

    override val viewModel: ReviewDetailViewModel by viewModels()

    private val commentAdapter: CommentAdapter by lazy {
        CommentAdapter(this::navigateToCommentDetail)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.hideKeyboardDown()

        bind {
            vm = viewModel
        }

        initRecyclerView()
        initObservers()
    }

    private fun initRecyclerView() {
        bind {
            commentRecyclerView.apply {
                adapter = commentAdapter
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.reviewInfo.collect {
                commentAdapter.submitList(it.comments.filter { comment -> comment.parentId == null })
            }
            viewModel.event.collect { event ->
                handleEvent(event)
            }
        }
    }

    private fun handleEvent(event: ReviewDetailViewModel.Event) {
        when (event) {
            is ReviewDetailViewModel.Event.AddComment -> {
                viewModel.registerComment(event.commentText)
                binding.root.hideKeyboardDown()
            }
            is ReviewDetailViewModel.Event.RemoveComment -> {
                // TODO
            }
            is ReviewDetailViewModel.Event.RemoveReview -> {
                // TODO
            }
        }
    }

    private fun navigateToCommentDetail(commentId: Int) {
        val action = ReviewDetailFragmentDirections.actionReviewDetailFragmentToCommentDetailFragment(viewModel.reviewId, commentId)
        (activity as MainActivity).navigateTo(action)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()

        (activity as MainActivity).showBottomNavigation()
    }
}