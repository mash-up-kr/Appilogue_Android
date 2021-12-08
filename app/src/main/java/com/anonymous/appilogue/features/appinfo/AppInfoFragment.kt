package com.anonymous.appilogue.features.appinfo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentAppInfoBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.community.reviewlist.ReviewListFragment
import com.anonymous.appilogue.features.community.reviewlist.ReviewListFragment.Companion.APP_INFO_FRAGMENT
import com.anonymous.appilogue.features.main.MainActivity
import com.anonymous.appilogue.utils.hideKeyboardDown
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AppInfoFragment
    : BaseFragment<FragmentAppInfoBinding, AppInfoViewModel>(R.layout.fragment_app_info) {

    override val viewModel: AppInfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.hideKeyboardDown()

        bind {
            vm = viewModel
        }

        initView()
        initObserver()
    }

    private fun initView() {
        bind {
            toolbarLeftIconView.setOnClickListener {
                activity?.onBackPressed()
            }
            writeReviewButtonView.setOnClickListener {
                val appInfo = viewModel.appInfo.value
                if (appInfo.name != null && appInfo.iconUrl != null) {
                    val action =
                        AppInfoFragmentDirections.actionAppInfoFragmentToReviewSelectorFragment(
                            appInfo.name,
                            appInfo.iconUrl
                        )
                    (activity as MainActivity).navigateTo(action)
                }
            }
            childFragmentManager.commit {
                replace(R.id.fragment_container, ReviewListFragment.newInstance(APP_INFO_FRAGMENT, ""))
                setReorderingAllowed(true)
            }
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.appInfo.collect {
                binding.blackHoleRatioView.initRatioView(viewModel.getBlackHoleRatio(), viewModel.isBlackHoleGreater())
                binding.whiteHoleRatioView.initRatioView(viewModel.getWhiteHoleRatio(), !viewModel.isBlackHoleGreater())
            }
        }
    }

    private fun TextView.initRatioView(ratio: Int, isGreater: Boolean) {
        text = getString(R.string.app_review_ratio_format, ratio)
        val textColor = if (isGreater) {
            ContextCompat.getColor(context, R.color.white)
        } else {
            ContextCompat.getColor(context, R.color.gray_04)
        }
        setTextColor(textColor)

        val colorStateList = if (isGreater) {
            ContextCompat.getColorStateList(context, R.color.purple_01)
        } else {
            ContextCompat.getColorStateList(context, R.color.gray_01)
        }
        backgroundTintList = colorStateList
    }
}