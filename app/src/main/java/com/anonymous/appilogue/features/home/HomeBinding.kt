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
fun setTitle(view: TextView, focus: Focus) {
    when (focus) {
        Focus.None -> view.context.getString(R.string.my_space)
        Focus.OnPlanet -> view.context.getString(R.string.planet)
        Focus.OnWhiteHole -> view.context.getString(R.string.white_hole)
        Focus.OnBlackHole -> view.context.getString(R.string.black_hole)
        Focus.OnSpaceDust -> view.context.getString(R.string.space_dust)
        else -> EMPTY_STRING
    }.run {
        view.text = this
    }
}

@BindingAdapter("state")
fun setState(layout: ConstraintLayout, newState: Int) {
    BottomSheetBehavior.from(layout).apply {
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
fun setVisible(view: View, focus: Focus) {
    when (focus) {
        Focus.OnPlanet, Focus.OnSpaceDust -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}

@BindingAdapter("firstTabTitle")
fun setFirstTabTitle(textView: TextView, focus: Focus) {
    when (focus) {
        Focus.OnPlanet -> textView.text = textView.context.getString(R.string.my_planet)
        Focus.OnSpaceDust -> textView.text = textView.context.getString(R.string.my_space_dust)
        Focus.OnBlackHole -> textView.text = textView.context.getString(R.string.my_black_hole)
        Focus.OnWhiteHole -> textView.text = textView.context.getString(R.string.my_white_hole)
        else -> textView.text = EMPTY_STRING
    }
}

@BindingAdapter("secondTabTitle")
fun setSecondTabTitle(textView: TextView, focus: Focus) {
    when (focus) {
        Focus.OnPlanet -> textView.text = textView.context.getString(R.string.planet_store)
        Focus.OnSpaceDust -> textView.text = textView.context.getString(R.string.dust_store)
        else -> textView.text = EMPTY_STRING
    }
}

const val EMPTY_STRING = ""
const val APP_ICON_RADIUS = 20