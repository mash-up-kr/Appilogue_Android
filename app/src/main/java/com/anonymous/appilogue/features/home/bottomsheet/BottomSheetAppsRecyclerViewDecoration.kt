package com.anonymous.appilogue.features.home

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.anonymous.appilogue.R
import com.anonymous.appilogue.utils.dpToPx


class BottomSheetAppsRecyclerViewDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val sideMargin = context.resources.getDimensionPixelSize(R.dimen.bottom_sheet_item_side_margin)
    private val bottomMargin = context.resources.getDimensionPixelSize(R.dimen.bottom_sheet_item_bottom_margin)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = sideMargin
        outRect.right = sideMargin
        outRect.bottom = bottomMargin
    }
}