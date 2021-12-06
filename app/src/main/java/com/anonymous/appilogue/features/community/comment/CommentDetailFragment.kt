package com.anonymous.appilogue.features.community.comment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentReviewCommentDetailBinding
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
class CommentDetailFragment
    : BaseFragment<FragmentReviewCommentDetailBinding, CommentDetailViewModel>(R.layout.fragment_review_comment_detail) {
    override val viewModel: CommentDetailViewModel by viewModels()

    private val commentDetailAdapter: CommentDetailAdapter by lazy {
        CommentDetailAdapter(this::showBottomSheetMenu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.hideKeyboardDown()

        bind {
            vm = viewModel
        }

        initView()
        initRecyclerView()
        initObserver()
    }

    private fun initView() {
        bind {
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.fetchComments()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun initRecyclerView() {
        bind {
            commentRecyclerView.apply {
                adapter = commentDetailAdapter
            }
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.comments.collect {
                commentDetailAdapter.submitList(it)
            }
        }
        lifecycleScope.launch {
            viewModel.event.collect {
                handleEvent(it)
            }
        }
    }

    private fun showBottomSheetMenu(commentModel: CommentModel) {
        val isMyComment = PreferencesManager.getUserId() == commentModel.user.id
        val bottomSheetMenu = BottomSheetMenuDialog(isMyComment) {
            if (isMyComment) {
                viewModel.removeCommentEvent(commentModel.id)
            } else {
                viewModel.reportCommentEvent(commentModel.id)
            }
        }
        (activity as MainActivity).showBottomSheetDialog(bottomSheetMenu)
    }

    private fun handleEvent(event: CommentDetailViewModel.Event) {
        when (event) {
            is CommentDetailViewModel.Event.AddNestedComment -> {
                viewModel.registerNestedComment(event.commentText)
                binding.root.hideKeyboardDown()
            }
            is CommentDetailViewModel.Event.RemoveComment -> {
                viewModel.removeComment(event.commentId)
            }
            is CommentDetailViewModel.Event.ReportComment -> {
                viewModel.reportComment(event.commentId)
            }
            is CommentDetailViewModel.Event.PressBackButton -> {
                activity?.onBackPressed()
            }
            is CommentDetailViewModel.Event.ShowToastForResult -> {
                val message = if (event.isMine) {
                    getString(R.string.remove_result_message)
                } else {
                    getString(R.string.report_result_message)
                }
                context?.showToast(message)
            }
        }
    }
}