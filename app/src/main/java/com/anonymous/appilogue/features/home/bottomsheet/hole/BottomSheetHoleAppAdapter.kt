package com.anonymous.appilogue.features.home.bottomsheet.hole

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.ItemAppBinding
import com.anonymous.appilogue.model.AppModel
import com.anonymous.appilogue.model.ReviewModel

class BottomSheetHoleAppAdapter constructor(
    private val navigateToAppInfo: (AppModel) -> Unit
) : ListAdapter<ReviewModel, BottomSheetHoleAppAdapter.BottomSheetAppViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetAppViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemAppBinding>(
            layoutInflater, R.layout.item_app, parent, false
        )
        return BottomSheetAppViewHolder(binding).apply {
            with(binding) {
                appContainer.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position in 0 until itemCount) {
                        getItem(position)?.let { item ->
                            navigateToAppInfo(item.app)
                        }
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BottomSheetAppViewHolder, position: Int) {
        if (position < itemCount) {
            holder.bind(getItem(position))
        }
    }

    class BottomSheetAppViewHolder(
        private val binding: ItemAppBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(review: ReviewModel) {
            binding.apply {
                item = review
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ReviewModel>() {
            override fun areItemsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}