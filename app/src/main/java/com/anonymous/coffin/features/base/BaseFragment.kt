package com.anonymous.coffin.features.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import java.lang.IllegalStateException


abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutResId: Int
) : Fragment(layoutResId) {

    abstract val viewModel: VM
    private lateinit var _binding: B
    protected val binding: B
        get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view) ?: throw IllegalStateException("Failed to bind the view on the Fragment.")
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    protected inline fun bind(block: B.() -> Unit) {
        binding.apply(block)
    }
}