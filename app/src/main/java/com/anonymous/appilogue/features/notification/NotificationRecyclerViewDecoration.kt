package com.anonymous.appilogue.features.notification

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.R

class NotificationRecyclerViewDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val sideMargin =
        context.resources.getDimensionPixelSize(R.dimen.notification_side_margin)
    private val bottomMargin =
        context.resources.getDimensionPixelSize(R.dimen.notification_bottom_margin)
    private val topMargin = context.resources.getDimensionPixelSize(R.dimen.notification_top_margin)
    private val dividerPaint: Paint =
        Paint().apply {
            color = ContextCompat.getColor(context, R.color.gray_01)
            style = Paint.Style.FILL
        }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = sideMargin
        outRect.right = sideMargin
        outRect.bottom = bottomMargin
        if (parent.getChildViewHolder(view) is NotificationAdapter.NotificationHeaderViewHolder
            && parent.getChildLayoutPosition(view) != 0
        ) {
            outRect.top = topMargin
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        parent.children.forEach { view ->
            if (parent.getChildViewHolder(view) is NotificationAdapter.NotificationHeaderViewHolder
                && parent.getChildLayoutPosition(view) != 0
            ) {
                c.drawRect(
                    view.left.toFloat() - sideMargin,
                    view.top.toFloat() - topMargin,
                    view.right.toFloat() + sideMargin,
                    view.top.toFloat() - topMargin + DIVIDER_HEIGHT,
                    dividerPaint
                )
            }
        }
    }

    companion object {
        const val DIVIDER_HEIGHT = 1
    }
}