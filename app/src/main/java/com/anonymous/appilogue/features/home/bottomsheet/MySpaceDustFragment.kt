package com.anonymous.appilogue.features.home.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.*
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentMySpaceDustBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.home.HomeViewModel
import com.anonymous.appilogue.features.home.SpaceDustItemDecoration
import com.anonymous.appilogue.features.home.onboarding.OnboardingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MySpaceDustFragment :
    BaseFragment<FragmentMySpaceDustBinding, HomeViewModel>(R.layout.fragment_my_space_dust) {
    override val viewModel: HomeViewModel by activityViewModels()
    private val _mySpaceDustViewModel: MySpaceDustViewModel by activityViewModels()
    private val spaceDustItemAdapter: SpaceDustItemAdapter by lazy {
        SpaceDustItemAdapter {
            _mySpaceDustViewModel.selectItem(it, true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            mySpaceDustViewModel = _mySpaceDustViewModel
            rvSpaceDustItems.apply {
                adapter = spaceDustItemAdapter
                addItemDecoration(SpaceDustItemDecoration(context))
            }
            tvSave.setOnClickListener {
                parentFragmentManager.commit {
                    add<SaveSpaceDustDialogFragment>(R.id.fcv_home)
                    setReorderingAllowed(true)
                }
            }
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return MySpaceDustFragment()
        }
    }
}