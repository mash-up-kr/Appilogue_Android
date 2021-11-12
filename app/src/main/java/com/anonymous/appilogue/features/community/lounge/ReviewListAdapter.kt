package com.anonymous.appilogue.features.community.lounge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.ItemLoungeFeedBinding
import com.anonymous.appilogue.model.ReviewInfo
import androidx.databinding.library.baseAdapters.BR

class ReviewListAdapter(
    private val viewModel: ReviewListViewModel
) : PagingDataAdapter<ReviewInfo, ReviewListAdapter.ReviewItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemLoungeFeedBinding>(
            layoutInflater,
            R.layout.item_lounge_feed,
            parent,
            false
        )
        return ReviewItemViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: ReviewItemViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class ReviewItemViewHolder(
        private val binding: ItemLoungeFeedBinding,
        private val viewModel: ReviewListViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(info: ReviewInfo) {
            binding.apply {
                setVariable(BR.item, info)
                setVariable(BR.vm, viewModel)
                executePendingBindings()
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ReviewInfo>() {
        override fun areItemsTheSame(oldInfo: ReviewInfo, newInfo: ReviewInfo): Boolean {
            return oldInfo.id == newInfo.id
        }

        override fun areContentsTheSame(oldInfo: ReviewInfo, newInfo: ReviewInfo): Boolean {
            return oldInfo == newInfo
        }
    }
}