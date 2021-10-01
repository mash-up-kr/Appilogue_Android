package com.anonymous.coffin.features.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.anonymous.coffin.R
import com.anonymous.coffin.databinding.ActivityMainBinding
import com.anonymous.coffin.features.base.BaseActivity
import com.anonymous.coffin.features.community.CommunityFragment
import com.anonymous.coffin.features.home.HomeFragment
import com.anonymous.coffin.features.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val homeFragment by lazy { HomeFragment() }
    private val profileFragment by lazy { ProfileFragment() }
    private val communityFragment by lazy { CommunityFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind {
            bottomNavigationView.setOnItemSelectedListener {
                navigateFragment(it)
                true
            }
        }
        setFragment(homeFragment)
    }

    private fun navigateFragment(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.action_home -> homeFragment
            R.id.action_profile -> profileFragment
            R.id.action_community -> communityFragment
            else -> null
        }?.let { fragment ->
            setFragment(fragment)
        }
    }

    private fun setFragment(fragment: Fragment) {
        val primaryFragment = supportFragmentManager.primaryNavigationFragment
        supportFragmentManager.commit {
            primaryFragment?.let { hide(it) }
            if (!fragment.isAdded) {
                add(R.id.fcv_fragmentContainerView, fragment)
            } else {
                show(fragment)
            }
            setPrimaryNavigationFragment(fragment)
        }
    }
}