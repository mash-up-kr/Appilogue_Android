package com.anonymous.appilogue.features.community.reviewlist

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentReviewListBinding
import com.anonymous.appilogue.features.appinfo.AppInfoFragmentDirections
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.community.lounge.LoungeFragmentDirections
import com.anonymous.appilogue.features.main.MainActivity
import com.anonymous.appilogue.model.AppModel
import com.anonymous.appilogue.model.ReviewModel
import com.anonymous.appilogue.persistence.PreferencesManager
import com.anonymous.appilogue.utils.showToast
import com.anonymous.appilogue.widget.BottomSheetMenuDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReviewListFragment
    : BaseFragment<FragmentReviewListBinding, ReviewListViewModel>(R.layout.fragment_review_list) {
    override val viewModel: ReviewListViewModel by viewModels()

    private val reviewListAdapter: ReviewListAdapter by lazy {
        ReviewListAdapter(
            viewModel,
            this::navigateToDetail,
            this::navigateToAppInfo,
            this::showBottomSheetMenu
        ).apply {
            addLoadStateListener { loadState ->
                if (loadState.source.refresh is LoadState.Loading) {
                    viewModel.beginToLoad()
                } else {
                    viewModel.completeToLoad()
                }

                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.also {
                    viewModel.setErrorMessage(it.error.message.toString())
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObservers()
    }

    private fun initView() {
        with(binding) {
            swipeRefreshLayout.setOnRefreshListener {
                lifecycleScope.launch {
                    reviewListAdapter.submitData(PagingData.empty())

                    viewModel.fetchReviewList()
                        .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                        .collectLatest {
                            swipeRefreshLayout.isRefreshing = false
                            reviewListAdapter.submitData(it)
                        }
                }
            }

            loungeRecyclerView.apply {
                adapter = reviewListAdapter
                ContextCompat.getDrawable(context, R.drawable.divider)?.let { divider ->
                    val itemDecoration = DividerItemDecoration(context, VERTICAL).apply {
                        setDrawable(divider)
                    }
                    addItemDecoration(itemDecoration)
                }
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.fetchReviewList()
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    reviewListAdapter.submitData(it)
                }
        }
        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.errorMessage.collect { errorMessage ->
                if (!errorMessage.isNullOrEmpty()) {
                    context?.showToast(errorMessage)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.event.collect {
                handleEvent(it)
            }
        }
    }

    private fun navigateToDetail(item: ReviewModel) {
        val action = if (viewModel.parentFragment == LOUNGE_FRAGMENT) {
            LoungeFragmentDirections.actionLoungeFragmentToReviewDetailFragment(item.id)
        } else {
            AppInfoFragmentDirections.actionAppInfoFragmentToReviewDetailFragment(item.id)
        }
        (activity as MainActivity).navigateTo(R.id.reviewDetailFragment, action)
    }

    private fun navigateToAppInfo(item: AppModel) {
        if (viewModel.parentFragment == LOUNGE_FRAGMENT) {
            val action = LoungeFragmentDirections.actionReviewListFragmentToAppInfoFragment(item)
            (activity as MainActivity).navigateTo(R.id.reviewListFragment, action)
        }
    }

    private fun showBottomSheetMenu(review: ReviewModel) {
        val isMyReview = PreferencesManager.getMyId() == review.user.id
        val bottomSheetMenu = BottomSheetMenuDialog(isMyReview) {
            if (isMyReview) {
                viewModel.removeReview(review.id)
            } else {
                viewModel.reportReview(review.id)
            }
        }
        (activity as MainActivity).showBottomSheetDialog(bottomSheetMenu)
    }

    private fun handleEvent(event: ReviewListViewModel.Event) {
        when (event) {
            is ReviewListViewModel.Event.PlusLike -> {
                viewModel.plusLike(event.reviewModel)
            }
            is ReviewListViewModel.Event.ShowToastForResult -> {
                val message = if (event.isMine) {
                    getString(R.string.remove_result_message)
                } else {
                    getString(R.string.report_result_message)
                }
                context?.showToast(message)
            }
        }
    }

    companion object {
        const val PARENT_FRAGMENT_KEY = "parent_fragment_key"
        const val HOLE_KEY = "hole"
        const val BLACK_HOLE = "black"
        const val WHITE_HOLE = "white"
        const val LOUNGE_FRAGMENT = "lounge"
        const val APP_INFO_FRAGMENT = "app_info"

        fun newInstance(parentFragmentName: String, hole: String): ReviewListFragment {
            val fragment = ReviewListFragment()
            val bundle = Bundle().apply {
                putString(PARENT_FRAGMENT_KEY, parentFragmentName)
                putString(HOLE_KEY, hole)
            }
            return fragment.apply {
                arguments = bundle
            }
        }
    }
}