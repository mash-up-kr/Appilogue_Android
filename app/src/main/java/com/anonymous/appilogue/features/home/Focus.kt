package com.anonymous.appilogue.features.home

enum class Focus(private val value: Int) {

    OnWhiteHole(1),
    OnBlackHole(2),
    OnSpaceDust(3),
    OnPlanet(4),
    None(0),
    OffWhiteHole(-1),
    OffBlackHole(-2),
    OffSpaceDust(-3),
    OffPlanet(-4);

    companion object {
        fun isOnFocus(focus: Focus): Boolean {
            return focus.value > 0
        }

        fun isOffFocus(focus: Focus): Boolean {
            return focus.value < 0
        }

        fun toOffFocus(focus: Focus): Focus? = values().find { it.value == -focus.value }
    }
}