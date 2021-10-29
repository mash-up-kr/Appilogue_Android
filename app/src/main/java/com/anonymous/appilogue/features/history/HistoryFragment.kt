package com.anonymous.appilogue.features.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentHistoryBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment :
    BaseFragment<FragmentHistoryBinding, HistoryViewModel>(R.layout.fragment_history) {
    override val viewModel: HistoryViewModel by activityViewModels()

    private val historyAdapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            historyViewModel = viewModel
        }
        initView()
        viewModel.fetchHistories() // fetch mock data
    }

    private fun initView() {
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).navigateTo(R.id.homeFragment)
        }
        binding.rvHistory.apply {
            adapter = historyAdapter
            addItemDecoration(HistoryRecyclerViewDecoration(context))
        }
    }
}