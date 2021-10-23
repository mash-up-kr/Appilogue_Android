package com.anonymous.appilogue.features.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.anonymous.appilogue.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetBehavior

@BindingAdapter("titleByFocus")
fun TextView.setTitleByFocus(focus: Focus) {
    when (focus) {
        Focus.None -> context.getString(R.string.my_space)
        Focus.OnPlanet -> context.getString(R.string.planet)
        Focus.OnWhiteHole -> context.getString(R.string.white_hole)
        Focus.OnBlackHole -> context.getString(R.string.black_hole)
        Focus.OnSpaceDust -> context.getString(R.string.space_dust)
        else -> EMPTY_STRING
    }.run {
        text = this
    }
}

@BindingAdapter("bottomSheetState")
fun ConstraintLayout.setBottomSheetState(newState: Int) {
    BottomSheetBehavior.from(this).apply {
        state = newState
    }
}

@BindingAdapter("appImageUrl")
fun ImageView.bindAppImageUrl(uri: String?) {
    if (!uri.isNullOrEmpty()) {
        Glide.with(context)
            .load(uri)
            .transform(CenterCrop(), RoundedCorners(APP_ICON_RADIUS))
            .into(this)
    }
}

const val EMPTY_STRING = ""
const val APP_ICON_RADIUS = 20