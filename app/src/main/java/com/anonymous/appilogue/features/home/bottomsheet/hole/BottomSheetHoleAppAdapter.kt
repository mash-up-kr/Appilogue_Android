package com.anonymous.appilogue.features.home.bottomsheet.hole

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.ItemAppBinding
import com.anonymous.appilogue.model.Review

class BottomSheetHoleAppAdapter :
    ListAdapter<Review, BottomSheetHoleAppAdapter.BottomSheetAppViewHolder>(diffCallback) {

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

        fun bind(review: Review) {
            binding.apply {
                item = review
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }
        }
    }
}