package com.anonymous.appilogue.features.community.comment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.BR
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.ItemReviewCommentBinding
import com.anonymous.appilogue.databinding.ItemReviewCommentNestedBinding
import com.anonymous.appilogue.model.CommentModel

class CommentDetailAdapter(
    private val showBottomSheetMenu: (CommentModel) -> Unit
) : RecyclerView.Adapter<CommentDetailAdapter.CommentViewHolder>() {
    private var items: List<CommentModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = when (viewType) {
            COMMENT_VIEW_TYPE -> {
                val binding = DataBindingUtil.inflate<ItemReviewCommentBinding>(
                    layoutInflater,
                    R.layout.item_review_comment,
                    parent,
                    false
                )
                CommentDetailViewHolder(binding).apply {
                    binding.moreButtonView.setOnClickListener {
                        val position = bindingAdapterPosition
                        if (position < itemCount) {
                            showBottomSheetMenu(items[position])
                        }
                    }
                }
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemReviewCommentNestedBinding>(
                    layoutInflater,
                    R.layout.item_review_comment_nested,
                    parent,
                    false
                )
                NestedCommentViewHolder(binding).apply {
                    binding.moreButtonView.setOnClickListener {
                        val position = bindingAdapterPosition
                        if (position < itemCount) {
                            showBottomSheetMenu(items[position])
                        }
                    }
                }
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = if (items[position].parentId == null) {
        COMMENT_VIEW_TYPE
    } else {
        NESTED_COMMENT_VIEW_TYPE
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(items: List<CommentModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    abstract class CommentViewHolder(
        open val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommentModel) {
            binding.apply {
                setVariable(BR.item, item)
                executePendingBindings()
            }
        }
    }

    class CommentDetailViewHolder(
        override val binding: ItemReviewCommentBinding
    ) : CommentViewHolder(binding)

    class NestedCommentViewHolder(
        override val binding: ItemReviewCommentNestedBinding
    ) : CommentViewHolder(binding)

    companion object {
        private const val COMMENT_VIEW_TYPE = 0
        private const val NESTED_COMMENT_VIEW_TYPE = 1
    }
}