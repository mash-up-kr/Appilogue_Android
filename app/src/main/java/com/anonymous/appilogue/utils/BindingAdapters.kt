package com.anonymous.appilogue.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.R
import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.model.HashTagModel
import com.anonymous.appilogue.model.LikesModel
import com.anonymous.appilogue.persistence.PreferencesManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("showHint")
fun TextInputLayout.bindShowHint(inputKeyword: String?) {
    this.isHintEnabled = inputKeyword == null
}

@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(uri: String?) {
    if (!uri.isNullOrEmpty()) {
        Glide.with(context)
            .load(uri)
            .apply(RequestOptions().fitCenter())
            .into(this)
    }
}

@BindingAdapter("profileUrl")
fun ImageView.bindProfileImageUrl(uri: String?) {
    if (!uri.isNullOrEmpty()) {
        Glide.with(context)
            .load(uri)
            .apply(RequestOptions().placeholder(R.drawable.space_dust).circleCrop().centerCrop())
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

@SuppressLint("SetTextI18n")
@BindingAdapter("tags")
fun ChipGroup.bindTags(tags: List<HashTagModel>?) {
    tags?.forEach { tag ->
        val tagView: Chip = Chip(context).apply {
            text = "#${tag.name}"
            isCheckable = false
            isCloseIconVisible = false
            setChipStrokeColorResource(R.color.purple_01)
            setChipStrokeWidthResource(R.dimen.chip_stroke_width)
            setChipBackgroundColorResource(R.color.black)
            setTextAppearanceResource(R.style.TextStyle_Chip)
        }
        addView(tagView)
    }
}

@BindingAdapter("bindCommentCount")
fun TextView.bindCommentCount(commentSize: Int) {
    if (commentSize == 0) {
        setText(R.string.comment_chip_text_empty)
    } else {
        val chipText = context.resources.getString(R.string.comment_count_text, commentSize)
        val countColor = ContextCompat.getColor(context, R.color.purple_01)
        val spannable = SpannableString(chipText).apply {
            setSpan(ForegroundColorSpan(countColor), 3, chipText.length, 0)
        }

        text = spannable
    }
}

@BindingAdapter("canShow")
fun View.bindCanShow(text: String?) {
    visibility = if (!text.isNullOrEmpty()) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("showProgress")
fun ProgressBar.bindShowProgress(uiState: UiState<*>) {
    this.visibility = if (uiState is UiState.Loading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("likeModels")
fun ImageView.bindLikeModels(likeModels: List<LikesModel>) {
    val myId = PreferencesManager.getUserId()
    isSelected = likeModels.any { it.user.id == myId }
}