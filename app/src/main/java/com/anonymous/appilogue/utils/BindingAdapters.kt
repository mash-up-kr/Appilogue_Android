package com.anonymous.appilogue.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
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
