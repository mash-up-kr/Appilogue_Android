package com.anonymous.appilogue.features.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.forEach
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.ActivityMainBinding
import com.anonymous.appilogue.features.base.BaseActivity
import com.anonymous.appilogue.features.search.AppSearchManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.lang.Compiler.enable


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
                    navigateTo(R.id.searchAppFragment)
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

        viewModel.bottomNavigationClickable.observe(this, { clickable ->
            binding.bottomNavigationView.menu.forEach {
                it.isEnabled = clickable
            }
        })
    }

    fun navigateTo(id: Int) {
        navController.navigate(id)
    }

    fun navigateTo(action: NavDirections) {
        navController.navigate(action)
    }

    fun showBottomNavigation() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun showBottomSheetDialog(bottomSheetDialogFragment: BottomSheetDialogFragment) {
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
    }
}
