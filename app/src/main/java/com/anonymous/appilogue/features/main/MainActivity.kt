package com.anonymous.appilogue.features.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.lifecycle.lifecycleScope
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.ActivityMainBinding
import com.anonymous.appilogue.features.base.BaseActivity
import com.anonymous.appilogue.features.search.AppSearchManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var appSearchManager: AppSearchManager

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

        lifecycleScope.launch(Dispatchers.IO) {
            appSearchManager.updateInstalledAppList()
        }
    }

    fun navigateTo(id: Int) {
        navController.navigate(id)
    }
}
