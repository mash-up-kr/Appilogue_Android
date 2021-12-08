package com.anonymous.appilogue.features.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.forEach
import androidx.navigation.fragment.NavHostFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.ui.setupWithNavController
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.ActivityMainBinding
import com.anonymous.appilogue.features.base.BaseActivity
import com.anonymous.appilogue.features.home.Focus
import com.anonymous.appilogue.features.home.HomeFragment
import com.anonymous.appilogue.features.home.HomeViewModel
import com.anonymous.appilogue.features.home.bottomsheet.space_dust.MySpaceDustViewModel
import com.anonymous.appilogue.features.search.AppSearchManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    val viewModel: MainViewModel by viewModels()
    val mySpaceDustViewModel: MySpaceDustViewModel by viewModels()
    val homeViewModel: HomeViewModel by viewModels()

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
        viewModel.fetchMyInformation()
    }

    private fun initNavigation() {
        with(binding.bottomNavigationView) {
            setupWithNavController(navController)
            itemIconTintList = null
            setOnItemSelectedListener {
                if (it.isChecked && it.itemId == R.id.homeFragment) {
                    navigateTo(R.id.searchAppFragment)
                } else {
                    navigateTo(it.itemId)
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
        mySpaceDustViewModel.fetchSpaceDustItems()
        viewModel.myUser.observe(this, {
            mySpaceDustViewModel.setMySpaceDust(it.profileImage)
        })
    }

    fun navigateTo(id: Int) {
        changeBottomNavigationStateByFragmentId(id)
        navController.navigate(id)
    }

    fun navigateTo(destination: Int, action: NavDirections) {
        changeBottomNavigationStateByFragmentId(destination)
        navController.navigate(action)
    }

    fun showBottomSheetDialog(bottomSheetDialogFragment: BottomSheetDialogFragment) {
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
    }

    private fun changeBottomNavigationStateByFragmentId(id: Int) {
        if (BottomNavigationStateById.bottomNavigationHidingStateById(id) == true) viewModel.hideBottomNavigation()
        else viewModel.showBottomNavigation()
    }

    override fun onBackPressed() {
        val nowFragment = supportFragmentManager.primaryNavigationFragment?.let {
            it.childFragmentManager.fragments[0]
        }
        if (nowFragment is HomeFragment) {
            homeViewModel.starFocused.value?.let { focus ->
                if (Focus.isOnFocus(focus)) {
                    homeViewModel.changeFocus(Focus.None)
                    return
                }
            }
        }
        super.onBackPressed()
        navController.currentDestination?.let {
            changeBottomNavigationStateByFragmentId(it.id)
        }
    }
}
