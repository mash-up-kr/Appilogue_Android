package com.anonymous.appilogue.features.login

import android.widget.Button
import androidx.core.content.ContextCompat
import com.anonymous.appilogue.R

object FirstButtonInit {
    fun buttonInit(button: Button) {
        with(button) {
            isEnabled = false
            context?.let { ctx ->
                setTextColor(ContextCompat.getColor(ctx, R.color.gray_01))
                background.setTint(ContextCompat.getColor(ctx, R.color.gray_02))
            }
        }
    }
}
