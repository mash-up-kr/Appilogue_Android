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
import com.anonymous.appilogue.features.community.CommunityFragment
import com.anonymous.appilogue.features.home.HomeFragment
import com.anonymous.appilogue.features.home.HomeViewModel
import com.anonymous.appilogue.features.profile.ProfileFragment
import com.anonymous.appilogue.features.search.SearchAppFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    val viewModel: HomeViewModel by viewModels()
    val mainViewModel: MainViewModel by viewModels()

    val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_main_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind {
            homeViewModel = viewModel
        }
        initNavigation()
    }

    private fun initNavigation() {
        with(binding) {
            bottomNavigationView.setupWithNavController(navController)
            bottomNavigationView.itemIconTintList = null
        }
    }

    fun navigateTo(id: Int) {
        navController.navigate(id)
    }
}