package com.anonymous.appilogue.features.community.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentReviewDetailBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity
import com.anonymous.appilogue.model.CommentModel
import com.anonymous.appilogue.persistence.PreferencesManager
import com.anonymous.appilogue.utils.hideKeyboardDown
import com.anonymous.appilogue.utils.showToast
import com.anonymous.appilogue.widget.BottomSheetMenuDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReviewDetailFragment
    : BaseFragment<FragmentReviewDetailBinding, ReviewDetailViewModel>(R.layout.fragment_review_detail) {

    override val viewModel: ReviewDetailViewModel by viewModels()

    private val commentAdapter: CommentAdapter by lazy {
        CommentAdapter(this::navigateToCommentDetail, this::showBottomSheetMenu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.hideKeyboardDown()

        bind {
            vm = viewModel
        }

        initView()
        initRecyclerView()
        initObservers()
    }

    private fun initView() {
        bind {
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.fetchReviews()
                swipeRefreshLayout.isRefreshing = false
            }

            toolbarRightIconView.setOnClickListener {
                val isMyReview = PreferencesManager.getMyId() == viewModel.getAuthorId()
                val bottomSheetMenu = BottomSheetMenuDialog(isMyReview) {
                    if (isMyReview) {
                        viewModel.removeReviewEvent()
                    } else {
                        viewModel.reportReviewEvent()
                    }
                }
                (activity as MainActivity).showBottomSheetDialog(bottomSheetMenu)
            }

            reviewContentView.appInfoContainer.setOnClickListener {
                viewModel.moveToAppInfoEvent()
            }

            reviewContentView.likeView.setOnClickListener { v ->
                if (!v.isSelected) {
                    val likeCount = viewModel.plusLikeEvent()
                    v.isSelected = true
                    reviewContentView.likeCountView.text = likeCount.toString()
                }
            }
        }
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
            viewModel.reviewModel.collect {
                commentAdapter.submitList(it.comments.filter { comment -> comment.parentId == null })
            }
        }
        lifecycleScope.launch {
            viewModel.event.collect {
                handleEvent(it)
            }
        }
    }

    private fun showBottomSheetMenu(commentModel: CommentModel) {
        val isMyComment = PreferencesManager.getMyId() == commentModel.user.id
        val bottomSheetMenu = BottomSheetMenuDialog(isMyComment) {
            if (isMyComment) {
                viewModel.removeCommentEvent(commentModel.id)
            } else {
                viewModel.reportCommentEvent(commentModel.id)
            }
        }
        (activity as MainActivity).showBottomSheetDialog(bottomSheetMenu)
    }

    private fun handleEvent(event: ReviewDetailViewModel.Event) {
        when (event) {
            is ReviewDetailViewModel.Event.AddComment -> {
                viewModel.registerComment(event.commentText)
                binding.root.hideKeyboardDown()
            }
            is ReviewDetailViewModel.Event.RemoveComment -> {
                viewModel.removeComment(event.commentId)
            }
            is ReviewDetailViewModel.Event.ReportComment -> {
                viewModel.reportComment(event.commentId)
                val index =
                    viewModel.reviewModel.value.comments.indexOfFirst { it.id == event.commentId }
                commentAdapter.notifyItemChanged(index)
            }
            is ReviewDetailViewModel.Event.RemoveReview -> {
                viewModel.removeReview()
                activity?.onBackPressed()
            }
            is ReviewDetailViewModel.Event.ReportReview -> {
                viewModel.reportReview()
                activity?.onBackPressed()
            }
            is ReviewDetailViewModel.Event.PressBackButton -> {
                activity?.onBackPressed()
            }
            is ReviewDetailViewModel.Event.ShowToastForResult -> {
                val message = if (event.isMine) {
                    getString(R.string.remove_result_message)
                } else {
                    getString(R.string.report_result_message)
                }
                context?.showToast(message)
            }
            is ReviewDetailViewModel.Event.MoveToAppInfo -> {
                val action = ReviewDetailFragmentDirections.actionReviewDetailFragmentToAppInfoFragment(event.appInfo)
                (activity as MainActivity).navigateTo(R.id.appInfoFragment, action)
            }
            is ReviewDetailViewModel.Event.PlusLike -> {
                viewModel.plusLike(event.reviewModel)
            }
        }
    }

    private fun navigateToCommentDetail(commentId: Int) {
        val action = ReviewDetailFragmentDirections.actionReviewDetailFragmentToCommentDetailFragment(viewModel.reviewId, commentId)
        (activity as MainActivity).navigateTo(R.id.commentDetailFragment, action)
    }
}