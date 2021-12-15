package com.anonymous.appilogue.features.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.ItemHistoryBinding
import com.anonymous.appilogue.databinding.ItemHistoryHeaderBinding

class HistoryAdapter :
    ListAdapter<HistoryListItem, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> {
                val binding = DataBindingUtil.inflate<ItemHistoryHeaderBinding>(
                    layoutInflater, R.layout.item_history_header, parent, false
                )
                HistoryHeaderViewHolder(binding)
            }
            ITEM_VIEW_TYPE_ITEM -> {
                val binding = DataBindingUtil.inflate<ItemHistoryBinding>(
                    layoutInflater, R.layout.item_history, parent, false
                )
                HistoryViewHolder(binding)
            }
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HistoryListItem.Header -> ITEM_VIEW_TYPE_HEADER
            is HistoryListItem.HistoryItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HistoryHeaderViewHolder -> holder.bind(getItem(position) as HistoryListItem.Header)
            is HistoryViewHolder -> holder.bind(getItem(position) as HistoryListItem.HistoryItem)
        }
    }

    class HistoryHeaderViewHolder(val binding: ItemHistoryHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(header: HistoryListItem.Header) {
            binding.apply {
                item = header
            }
        }
    }

    class HistoryViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(history: HistoryListItem.HistoryItem) {
            binding.apply {
                item = history
            }
        }
    }

    companion object {
        const val ITEM_VIEW_TYPE_HEADER = 0
        const val ITEM_VIEW_TYPE_ITEM = 1
        private val diffCallback = object : DiffUtil.ItemCallback<HistoryListItem>() {

            override fun areContentsTheSame(
                oldItem: HistoryListItem,
                newItem: HistoryListItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(
                oldItem: HistoryListItem,
                newItem: HistoryListItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}