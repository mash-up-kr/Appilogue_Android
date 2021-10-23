package com.anonymous.appilogue.features.home.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.ItemAppBinding
import com.anonymous.appilogue.model.ReviewedApp

class BottomSheetAppAdapter :
    ListAdapter<ReviewedApp, BottomSheetAppAdapter.BottomSheetAppViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetAppViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemAppBinding>(
            layoutInflater, R.layout.item_app, parent, false
        )
        return BottomSheetAppViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BottomSheetAppViewHolder, position: Int) {
        if (position < itemCount) {
            holder.bind(getItem(position))
        }
    }

    class BottomSheetAppViewHolder(
        private val binding: ItemAppBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reviewedApp: ReviewedApp) {
            binding.apply {
                item = reviewedApp
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ReviewedApp>() {
            override fun areItemsTheSame(oldItem: ReviewedApp, newItem: ReviewedApp): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: ReviewedApp, newItem: ReviewedApp): Boolean {
                return oldItem == newItem
            }
        }
    }
}