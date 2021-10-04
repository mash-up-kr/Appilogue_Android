package com.anonymous.appilogue.features.home

enum class Focus {

    None,
    OnWhiteHole,
    OnBlackHole,
    OnSpaceDust,
    OnPlanet,
    OffWhiteHole,
    OffBlackHole,
    OffSpaceDust,
    OffPlanet;

    companion object {
        const val STAR_NUM = 4
        fun from(findValue: Int): Focus = values().first { it.ordinal == findValue }
    }
}