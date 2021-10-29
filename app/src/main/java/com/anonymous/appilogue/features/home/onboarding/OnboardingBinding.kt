package com.anonymous.appilogue.features.home.onboarding

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.anonymous.appilogue.R

@BindingAdapter("onboardingDialogText", "emphasizedText")
fun TextView.setOnboardingDialogText(onboardingDialogStringId: Int, emphaziedStringId: Int) {
    val onboardingDialogString = context.getString(onboardingDialogStringId)
    val emphaziedString = context.getString(emphaziedStringId)
    text = SpannableString(onboardingDialogString).apply {
        if (onboardingDialogString.contains(emphaziedString))
            setSpan(
                ForegroundColorSpan(context.getColor(R.color.mint)),
                onboardingDialogString.indexOf(emphaziedString),
                onboardingDialogString.indexOf(emphaziedString) + emphaziedString.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
    }
}