package com.anonymous.appilogue.features.home

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.anonymous.appilogue.R


class SpaceDustItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val sideMargin =
        context.resources.getDimensionPixelSize(R.dimen.bottom_sheet_space_dust_item_side_margin)
    private val span = 5

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = sideMargin
        outRect.right = sideMargin
    }
}