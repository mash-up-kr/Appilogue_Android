package com.anonymous.appilogue.features.home

data class StarsAlpha(
    val planetAlpha: Float = DEFAULT_ALPHA,
    val blackHoleAlpha: Float = DEFAULT_ALPHA,
    val whiteHoleAlpha: Float = DEFAULT_ALPHA,
    val spaceDustAlpha: Float = DEFAULT_ALPHA
) {
    companion object {
        const val DEFAULT_ALPHA = 0.3f
    }
}

