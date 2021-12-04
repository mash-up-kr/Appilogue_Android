package com.anonymous.appilogue.features.home.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.ItemAppBinding
import com.anonymous.appilogue.databinding.ItemSpaceDustItemBinding
import com.anonymous.appilogue.model.Review
import com.anonymous.appilogue.model.ReviewedApp
import com.anonymous.appilogue.model.SelectableSpaceDustItem

class SpaceDustItemAdapter(private val clickEvent: (Int) -> Unit) :
    ListAdapter<SelectableSpaceDustItem, SpaceDustItemAdapter.SpaceDustItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceDustItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSpaceDustItemBinding>(
            layoutInflater, R.layout.item_space_dust_item, parent, false
        )
        return SpaceDustItemViewHolder(binding, clickEvent)
    }

    override fun onBindViewHolder(holder: SpaceDustItemViewHolder, position: Int) {
        if (position < itemCount) {
            holder.bind(getItem(position))
        }
    }

    class SpaceDustItemViewHolder(
        private val binding: ItemSpaceDustItemBinding,
        private val clickEvent: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return@setOnClickListener
                clickEvent.invoke(position)
            }
        }

        fun bind(spaceDustItem: SelectableSpaceDustItem) {
            binding.apply {
                item = spaceDustItem
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SelectableSpaceDustItem>() {
            override fun areItemsTheSame(
                oldItem: SelectableSpaceDustItem,
                newItem: SelectableSpaceDustItem
            ): Boolean {
                return oldItem.spaceDustItem.id == newItem.spaceDustItem.id
            }

            override fun areContentsTheSame(
                oldItem: SelectableSpaceDustItem,
                newItem: SelectableSpaceDustItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}