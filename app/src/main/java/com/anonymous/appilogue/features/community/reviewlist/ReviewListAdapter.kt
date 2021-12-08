package com.anonymous.appilogue.features.community.reviewlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.R
import com.anonymous.appilogue.model.ReviewModel
import androidx.databinding.library.baseAdapters.BR
import com.anonymous.appilogue.databinding.ItemReviewContentBinding
import com.anonymous.appilogue.model.AppModel

class ReviewListAdapter(
    private val viewModel: ReviewListViewModel,
    private val navigateToDetail: (ReviewModel) -> Unit,
    private val navigateToAppInfo: (AppModel) -> Unit,
    private val showBottomSheetMenu: (ReviewModel) -> Unit
) : PagingDataAdapter<ReviewModel, ReviewListAdapter.ReviewItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemReviewContentBinding>(
            layoutInflater,
            R.layout.item_review_content,
            parent,
            false
        )
        return ReviewItemViewHolder(binding, viewModel).apply {
            initViewHolder(binding)
        }
    }

    private fun ReviewItemViewHolder.initViewHolder(binding: ItemReviewContentBinding) {
        with(binding) {
            feedContainer.setOnClickListener {
                val position = bindingAdapterPosition
                if (position in 1 until itemCount) {
                    getItem(position)?.let {
                        navigateToDetail(it)
                    }
                }
            }
            moreButtonView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position in 1 until itemCount) {
                    getItem(position)?.let { review ->
                        showBottomSheetMenu(review)
                    }
                }
            }
            appInfoContainer.setOnClickListener {
                val position = bindingAdapterPosition
                if (position in 1 until itemCount) {
                    getItem(position)?.let { review ->
                        navigateToAppInfo(review.app)
                    }
                }
            }
            likeView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position in 1 until itemCount) {
                    getItem(position)?.let { review ->
                        if (!it.isSelected) {
                            val likesCount = viewModel.plusLikeEvent(review)
                            it.isSelected = true
                            likeCountView.text = likesCount.toString()
                        }
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ReviewItemViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class ReviewItemViewHolder(
        private val binding: ItemReviewContentBinding,
        private val viewModel: ReviewListViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReviewModel) {
            if (viewModel.hole.isEmpty()) {
                binding.holeView.apply {
                    visibility = View.VISIBLE
                    if (item.hole == ReviewListFragment.BLACK_HOLE) {
                        text = context.getString(R.string.black_hole_text)
                        setTextColor(ContextCompat.getColor(context, R.color.purple_01))
                        backgroundTintList =
                            ContextCompat.getColorStateList(context, R.color.purple_02)
                    } else {
                        text = context.getString(R.string.white_hole_text)
                        setTextColor(ContextCompat.getColor(context, R.color.mint_01))
                        backgroundTintList =
                            ContextCompat.getColorStateList(context, R.color.mint_02)
                    }
                }
            }

            binding.apply {
                setVariable(BR.item, item)
                setVariable(BR.vm, viewModel)
                executePendingBindings()
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ReviewModel>() {
        override fun areItemsTheSame(oldModel: ReviewModel, newModel: ReviewModel): Boolean {
            return oldModel.id == newModel.id
        }

        override fun areContentsTheSame(oldModel: ReviewModel, newModel: ReviewModel): Boolean {
            return oldModel == newModel
        }
    }
}