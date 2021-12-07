package com.anonymous.appilogue.features.home.bottomsheet.space_dust

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentStoreBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.home.HomeViewModel

class StoreFragment :
    BaseFragment<FragmentStoreBinding, HomeViewModel>(R.layout.fragment_store) {
    override val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            homeViewModel = viewModel
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return StoreFragment()
        }
    }
}