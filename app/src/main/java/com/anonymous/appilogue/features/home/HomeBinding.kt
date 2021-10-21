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
import org.w3c.dom.Text

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

@BindingAdapter("secondTabVisible")
fun View.setSecondTabVisible(focus: Focus) {
    visibility = when (focus) {
        Focus.OnPlanet, Focus.OnSpaceDust -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("firstTabTitle")
fun TextView.setFirstTabTitle(focus: Focus) {
    text = when (focus) {
        Focus.OnPlanet -> context.getString(R.string.my_planet)
        Focus.OnSpaceDust -> context.getString(R.string.my_space_dust)
        Focus.OnBlackHole -> context.getString(R.string.my_black_hole)
        Focus.OnWhiteHole -> context.getString(R.string.my_white_hole)
        else -> EMPTY_STRING
    }
}

@BindingAdapter("secondTabTitle")
fun TextView.setSecondTabTitle(focus: Focus) {
    text = when (focus) {
        Focus.OnPlanet -> context.getString(R.string.planet_store)
        Focus.OnSpaceDust -> context.getString(R.string.dust_store)
        else -> EMPTY_STRING
    }
}

const val EMPTY_STRING = ""
const val APP_ICON_RADIUS = 20