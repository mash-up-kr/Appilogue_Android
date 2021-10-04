package com.anonymous.appilogue.features.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.anonymous.appilogue.R

@BindingAdapter("titleByFocus")
fun setTitle(view: TextView, focus: Focus) {
    when (focus) {
        Focus.None -> view.context.getString(R.string.my_space)
        Focus.OnPlanet -> view.context.getString(R.string.planet)
        Focus.OnWhiteHole -> view.context.getString(R.string.white_hole)
        Focus.OnBlackHole -> view.context.getString(R.string.black_hole)
        Focus.OnSpaceDust -> view.context.getString(R.string.space_dust)
        else -> String()
    }.run {
        view.text = this
    }
}