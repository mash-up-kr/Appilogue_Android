package com.anonymous.appilogue.utils

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.R
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("showHint")
fun TextInputLayout.bindShowHint(inputKeyword: String?) {
    this.isHintEnabled = inputKeyword.isNullOrEmpty()
}

@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(uri: String?) {
    if (!uri.isNullOrEmpty()) {
        Glide.with(context)
            .load(uri)
            .into(this)
    }
}

@BindingAdapter("bindBitmap")
fun ImageView.bindBitmap(bitmap: Bitmap?) {
    if (bitmap != null) {
        Glide.with(context)
            .load(bitmap)
            .into(this)
    }
}

@BindingAdapter("show")
fun TextView.bindShow(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("isSelected")
fun TextView.bindIsSelected(selected: Boolean) {
    if (selected) {
        val viewBackground = background
        val wrap = DrawableCompat.wrap(viewBackground)
        DrawableCompat.setTint(wrap, context.getColor(R.color.purple_01))
        background = wrap
        setTextColor(context.getColor(R.color.white))
    }
}

@BindingAdapter("replaceList")
fun RecyclerView.replaceList(list: List<Any>?) {
    @Suppress("UNCHECKED_CAST")
    (this.adapter as ListAdapter<Any, RecyclerView.ViewHolder>).run {
        this.submitList(list)
    }
}

@BindingAdapter("reviewTitle")
fun TextView.bindReviewTitle(isBlackHoleReview: Boolean?) {
    if (isBlackHoleReview != true) {
        text = context.getString(R.string.abb_bar_white_hole_review_title)
    }
}