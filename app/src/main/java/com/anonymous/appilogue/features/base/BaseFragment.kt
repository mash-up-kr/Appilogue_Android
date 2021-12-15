package com.anonymous.appilogue.features.base

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController


abstract class BaseFragment<B : ViewDataBinding, out VM : ViewModel>(
    @LayoutRes private val layoutResId: Int,
) : Fragment(layoutResId) {

    abstract val viewModel: VM
    private lateinit var _binding: B
    protected val binding: B
        get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view)
            ?: throw IllegalStateException("Failed to bind the view on the Fragment.")
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
        activity?.onBackPressedDispatcher?.addCallback(this) {
            val navController = findNavController()
            val backStackId = navController.currentBackStackEntry?.destination?.id
            if (backStackId != null) {
                findNavController().popBackStack(
                    backStackId,
                    true
                )
            } else {
                navController.popBackStack()
            }
        }
    }

    protected inline fun bind(block: B.() -> Unit) {
        binding.apply(block)
    }
}
