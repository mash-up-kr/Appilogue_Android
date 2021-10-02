package com.anonymous.appilogue.features.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.ActivityMainBinding
import com.anonymous.appilogue.features.base.BaseActivity
import com.anonymous.appilogue.features.community.CommunityFragment
import com.anonymous.appilogue.features.home.HomeFragment
import com.anonymous.appilogue.features.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val homeFragment by lazy { HomeFragment() }
    private val profileFragment by lazy { ProfileFragment() }
    private val communityFragment by lazy { CommunityFragment() }

    val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind {
            homeViewModel = viewModel
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            navigateFragment(it)
            true
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