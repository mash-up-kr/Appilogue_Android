package com.anonymous.appilogue.features.community.comment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentReviewCommentDetailBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.utils.hideKeyboardDown
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentDetailFragment
    : BaseFragment<FragmentReviewCommentDetailBinding, CommentDetailViewModel>(R.layout.fragment_review_comment_detail) {
    override val viewModel: CommentDetailViewModel by viewModels()

    private val commentDetailAdapter: CommentDetailAdapter by lazy {
        CommentDetailAdapter()
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

    private fun handleEvent(event: CommentDetailViewModel.Event) {
        when (event) {
            is CommentDetailViewModel.Event.AddNestedComment -> {
                viewModel.registerNestedComment(event.commentText)
                binding.root.hideKeyboardDown()
            }
            is CommentDetailViewModel.Event.PressBackButton -> {
                activity?.onBackPressed()
            }
        }
    }
}