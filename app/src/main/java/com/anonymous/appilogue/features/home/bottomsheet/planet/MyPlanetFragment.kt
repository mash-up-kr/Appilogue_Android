package com.anonymous.appilogue.features.home.bottomsheet.planet

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentMyPlanetBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPlanetFragment :
    BaseFragment<FragmentMyPlanetBinding, HomeViewModel>(R.layout.fragment_my_planet) {
    override val viewModel: HomeViewModel by activityViewModels()
    companion object {
        fun newInstance(): Fragment {
            return MyPlanetFragment()
        }
    }
}