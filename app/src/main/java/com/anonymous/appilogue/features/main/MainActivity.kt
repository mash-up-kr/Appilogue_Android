package com.anonymous.appilogue.features.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
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

    val viewModel: MainViewModel by viewModels()

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_main_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind {
            mainViewModel = viewModel
        }
        initNavigation()
    }

    private fun initNavigation() {
        with(binding.bottomNavigationView) {
            setupWithNavController(navController)
            itemIconTintList = null
            setOnItemSelectedListener {
                if (it.isChecked && it.itemId == R.id.homeFragment) {
                    navigateTo(R.id.searchAppFragment2)
                    viewModel.hideBottomNavigation()
                } else {
                    navigateTo(it.itemId)
                    viewModel.showBottomNavigation()
                }
                true
            }
        }
    }

    fun navigateTo(id: Int) {
        navController.navigate(id)
    }
}