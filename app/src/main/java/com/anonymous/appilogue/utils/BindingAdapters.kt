package com.anonymous.appilogue.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.R
import com.anonymous.appilogue.features.search.SearchAppAdapter
import com.anonymous.appilogue.model.InstalledApp
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

@BindingAdapter("selectBlackHole", requireAll = true)
fun TextView.bindText(isSelectBlackHole: Boolean) {
    text = if (isSelectBlackHole) {
        context.getString(R.string.description_black_hole)
    } else {
        context.getString(R.string.description_white_hole)
    }
}

@BindingAdapter("isSelected")
fun TextView.bindIsSelected(selected: Boolean) {
    if (selected) {
        setBackgroundColor(context.getColor(R.color.purple_01))
        setTextColor(context.getColor(R.color.white))
    }
}