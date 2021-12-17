package com.anonymous.appilogue.features.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.forEach
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.ActivityMainBinding
import com.anonymous.appilogue.features.base.BaseActivity
import com.anonymous.appilogue.features.home.Focus
import com.anonymous.appilogue.features.home.HomeViewModel
import com.anonymous.appilogue.features.home.bottomsheet.space_dust.MySpaceDustViewModel
import com.anonymous.appilogue.features.search.AppSearchManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.exitProcess


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    val viewModel: MainViewModel by viewModels()
    val mySpaceDustViewModel: MySpaceDustViewModel by viewModels()
    val homeViewModel: HomeViewModel by viewModels()

    private var disposable: Disposable? = null
    private val behaviorSubject = BehaviorSubject.createDefault(0L)

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
        doubleClickBackPressed()
    }

    private fun doubleClickBackPressed() {
        disposable = behaviorSubject.buffer(2, 1)
            .map { it[0] to it[1] }
            .subscribe {
                if (it.second - it.first < 2000L) {
                    super.onBackPressed()
                    moveTaskToBack(true)
                    this.finishAndRemoveTask()
                    exitProcess(0)
                } else {
                    Toast.makeText(this, "앱을 종료하려면 한번 더 눌러주세요", Toast.LENGTH_SHORT).show()
                }
            }
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

    fun navigateTo(action: NavDirections) {
        changeBottomNavigationStateByFragmentId(action.actionId)
        navController.navigate(action)
    }

    fun showBottomSheetDialog(bottomSheetDialogFragment: BottomSheetDialogFragment) {
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
    }

    private fun changeBottomNavigationStateByFragmentId(id: Int) {
        if (BottomNavigationStateById.bottomNavigationHidingStateById(id) == true) viewModel.hideBottomNavigation()
        else viewModel.showBottomNavigation()
    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        navController.currentDestination?.let {
            if (it.id == R.id.homeFragment) {
                homeViewModel.starFocused.value?.let { focus ->
                    if (Focus.isOnFocus(focus)) {
                        homeViewModel.changeFocus(Focus.None)
                        return
                    }
                }
            }
        }
        if (navController.backStack.count() <= 2) {
            behaviorSubject.onNext(System.currentTimeMillis())
        } else {
            super.onBackPressed()
        }

        navController.currentDestination?.let {
            changeBottomNavigationStateByFragmentId(it.id)
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
