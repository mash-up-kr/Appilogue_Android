package com.anonymous.appilogue.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
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
import timber.log.Timber


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
    val viewBackground = background.constantState?.newDrawable()?.mutate() ?: return
    if (selected) {
        DrawableCompat.setTint(viewBackground, context.getColor(R.color.purple_01))
        background = viewBackground
        setTextColor(context.getColor(R.color.white))
    } else {
        DrawableCompat.setTint(viewBackground, context.getColor(R.color.gray_01))
        background = viewBackground
        setTextColor(context.getColor(R.color.gray_02))
    }
}

@BindingAdapter("replaceList")
fun RecyclerView.replaceList(list: List<Any>?) {
    Timber.d(list.toString())
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
    removeAllViews()
    tags?.forEach { tag ->
        val tagView: Chip = Chip(context).apply {
            text = tag.name
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
    val myId = PreferencesManager.getMyId()
    isSelected = likeModels.any { it.user.id == myId }
}

@BindingAdapter("underlineText")
fun TextView.bindUnderlineText(str: String?) {
    if (!str.isNullOrEmpty()) {
        val spannable = SpannableString(str).apply {
            setSpan(UnderlineSpan(), 0, str.length, 0)
        }
        text = spannable
    }
}

@BindingAdapter("isAgreed")
fun ImageView.bindIsAgreed(isAgreed: Boolean) {
    isSelected = isAgreed
}

@BindingAdapter("textColorForAgreement")
fun TextView.bindTextColorForAgreement(isAgreed: Boolean) {
    val textColor = if (isAgreed) {
        ContextCompat.getColor(context, R.color.white)
    } else {
        ContextCompat.getColor(context, R.color.gray_02)
    }
    setTextColor(textColor)
}