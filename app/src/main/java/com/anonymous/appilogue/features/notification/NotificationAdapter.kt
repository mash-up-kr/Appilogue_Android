package com.anonymous.appilogue.features.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.ItemNotificationBinding
import com.anonymous.appilogue.databinding.ItemNotificationHeaderBinding

class NotificationAdapter :
    ListAdapter<NotificationListItem, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> {
                val binding = DataBindingUtil.inflate<ItemNotificationHeaderBinding>(
                    layoutInflater, R.layout.item_notification_header, parent, false
                )
                NotificationHeaderViewHolder(binding)
            }
            ITEM_VIEW_TYPE_ITEM -> {
                val binding = DataBindingUtil.inflate<ItemNotificationBinding>(
                    layoutInflater, R.layout.item_notification, parent, false
                )
                NotificationViewHolder(binding)
            }
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is NotificationListItem.Header -> ITEM_VIEW_TYPE_HEADER
            is NotificationListItem.NotificationItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NotificationHeaderViewHolder -> holder.bind(getItem(position) as NotificationListItem.Header)
            is NotificationViewHolder -> holder.bind(getItem(position) as NotificationListItem.NotificationItem)
        }
    }

    class NotificationHeaderViewHolder(val binding: ItemNotificationHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(header: NotificationListItem.Header) {
            binding.apply {
                item = header
            }
        }
    }

    class NotificationViewHolder(val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: NotificationListItem.NotificationItem) {
            binding.apply {
                item = notification
            }
        }
    }

    companion object {
        const val ITEM_VIEW_TYPE_HEADER = 0
        const val ITEM_VIEW_TYPE_ITEM = 1
        private val diffCallback = object : DiffUtil.ItemCallback<NotificationListItem>() {

            override fun areContentsTheSame(
                oldItem: NotificationListItem,
                newItem: NotificationListItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(
                oldItem: NotificationListItem,
                newItem: NotificationListItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}