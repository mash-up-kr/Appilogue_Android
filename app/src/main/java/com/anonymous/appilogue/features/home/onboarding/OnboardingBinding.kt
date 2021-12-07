package com.anonymous.appilogue.features.home.onboarding

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.anonymous.appilogue.R

@BindingAdapter("onboardingDialogText", "emphasizedText")
fun TextView.setOnboardingDialogText(onboardingDialogStringId: Int, emphasizedStringId: Int) {
    val onboardingDialogString = context.getString(onboardingDialogStringId)
    val emphasizedString = context.getString(emphasizedStringId)
    text = SpannableString(onboardingDialogString).apply {
        if (onboardingDialogString.contains(emphasizedString))
            setSpan(
                ForegroundColorSpan(context.getColor(R.color.mint)),
                onboardingDialogString.indexOf(emphasizedString),
                onboardingDialogString.indexOf(emphasizedString) + emphasizedString.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
    }
}