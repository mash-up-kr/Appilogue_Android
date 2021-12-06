package com.anonymous.appilogue.features.community.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.R
import androidx.databinding.library.baseAdapters.BR
import com.anonymous.appilogue.databinding.ItemReviewCommentBinding
import com.anonymous.appilogue.model.CommentModel
import com.anonymous.appilogue.widget.BottomSheetMenuDialog

class CommentAdapter(
    private val navigateToCommentDetail: (Int) -> Unit,
    private val showBottomSheetMenu: (CommentModel) -> Unit
) : ListAdapter<CommentModel, CommentAdapter.CommentViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemReviewCommentBinding>(
            layoutInflater,
            R.layout.item_review_comment,
            parent,
            false
        )
        return CommentViewHolder(binding).apply {
            with(binding) {
                commentLabelView.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position < itemCount) {
                        getItem(position)?.let {
                            navigateToCommentDetail(it.id)
                        }
                    }
                }

                moreButtonView.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position < itemCount) {
                        getItem(position)?.let {
                            showBottomSheetMenu(it)
                        }
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class CommentViewHolder(
        private val binding: ItemReviewCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: CommentModel) {
            binding.apply {
                setVariable(BR.item, comment)
                executePendingBindings()
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CommentModel>() {
            override fun areItemsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}