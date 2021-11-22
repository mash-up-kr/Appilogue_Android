package com.anonymous.appilogue.features.home

sealed class StarEmphasizeState(
    val planetAlpha: Float = UN_EMPHASIZED_ALPHA,
    val blackHoleAlpha: Float = UN_EMPHASIZED_ALPHA,
    val whiteHoleAlpha: Float = UN_EMPHASIZED_ALPHA,
    val spaceDustAlpha: Float = UN_EMPHASIZED_ALPHA,
) {

    class EmphasizeOnPlanet : StarEmphasizeState(planetAlpha = EMPHASIZED_ALPHA)
    class EmphasizeOnBlackHole : StarEmphasizeState(blackHoleAlpha = EMPHASIZED_ALPHA)
    class EmphasizeOnWhiteHole : StarEmphasizeState(whiteHoleAlpha = EMPHASIZED_ALPHA)
    class EmphasizeOnSpaceDust : StarEmphasizeState(spaceDustAlpha = EMPHASIZED_ALPHA)
    class EmphasizeOnAllStar :
        StarEmphasizeState(EMPHASIZED_ALPHA, EMPHASIZED_ALPHA, EMPHASIZED_ALPHA, EMPHASIZED_ALPHA)

    companion object {
        const val UN_EMPHASIZED_ALPHA = 0.3f
        const val EMPHASIZED_ALPHA = 1f
    }
}

