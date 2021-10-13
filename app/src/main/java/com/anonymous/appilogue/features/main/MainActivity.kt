package com.anonymous.appilogue.features.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.ActivityMainBinding
import com.anonymous.appilogue.features.base.BaseActivity
import com.anonymous.appilogue.features.home.HomeFragment
import com.anonymous.appilogue.features.home.HomeViewModel
import com.anonymous.appilogue.features.profile.ProfileFragment
import com.anonymous.appilogue.features.search.SearchAppFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val homeFragment by lazy { HomeFragment() }
    private val profileFragment by lazy { ProfileFragment() }
    private val communityFragment by lazy { SearchAppFragment() }

    val viewModel: HomeViewModel by viewModels()
    val mainViewModel: MainViewModel by viewModels()

    val navigateTo: (Fragment) -> Unit = { fragment ->
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind {
            homeViewModel = viewModel
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            navigateFragment(it)
            true
        }
        navigateTo(homeFragment)
    }

    private fun navigateFragment(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.action_home -> homeFragment
            R.id.action_profile -> profileFragment
            R.id.action_community -> communityFragment
            else -> null
        }?.let { fragment ->
            navigateTo(fragment)
        }
    }

    fun hideBottomNavigation() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun showBottomNavigation() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }
}
