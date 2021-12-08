package com.anonymous.appilogue.features.main

import com.anonymous.appilogue.R

enum class BottomNavigationStateById(val id: Int, val isHiding: Boolean) {
    HOME(R.id.homeFragment, false),
    COMMUNITY(R.id.loungeFragment, false),
    PROFILE(R.id.profileFragment, false),
    PROFILE_SETTING(R.id.profileSettingFragment, true),
    SEARCH_APP_FRAGMENT(R.id.searchAppFragment, true),
    REVIEW_SELECTOR_FRAGMENT(R.id.reviewSelectorFragment, true),
    REVIEW_REGISTER_FRAGMENT(R.id.reviewRegisterFragment, true),
    SEARCH_APP_FRAGMENT_TO_REVIEW_SELECTOR_FRAGMENT(
        R.id.action_searchAppFragment_to_reviewSelectorFragment,
        true
    ),
    REVIEW_SELECTOR_FRAGMENT_TO_REVIEW_REGISTER_FRAGMENT(
        R.id.action_reviewSelectorFragment_to_reviewRegisterFragment,
        true
    );

    companion object {
        fun bottomNavigationHidingStateById(id: Int): Boolean? {
            return values().find { it.id == id }?.isHiding
        }
    }
}