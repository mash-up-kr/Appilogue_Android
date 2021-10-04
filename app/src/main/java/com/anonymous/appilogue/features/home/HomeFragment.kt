package com.anonymous.appilogue.features.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentHomeBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private val stars by lazy {
        with(binding) {
            listOf(ivBlackHole, ivMainPlanet, ivSpaceDust, ivWhiteHole)
        }
    }

    override val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomSheet()
        bind {
            homeViewModel = viewModel
        }
        observeStar()
    }

    private fun initBottomSheet() {
        binding.bottomSheetHome.root.apply {
            updateLayoutParams {
                height = Resources.getSystem().displayMetrics.heightPixels -
                        resources.getDimension(R.dimen.expanded_bottomsheet_home_margin).toInt()
            }
            BottomSheetBehavior.from(this).apply {
                peekHeight = (resources.getDimension(R.dimen.peek_bottomsheet_home)).toInt()
                viewModel.changeBottomSheetState(BottomSheetBehavior.STATE_HIDDEN)
                addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        viewModel.changeBottomSheetState(newState)
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        listOf(binding.ivBackButton, binding.tvBackToHome).forEach {
                            apply {
                                it.alpha = slideOffset + 1
                            }
                        }
                    }
                })
            }
            viewModel.bottomSheetState.observe(viewLifecycleOwner, { newState ->
                BottomSheetBehavior.from(this).apply {
                    state = newState
                }
            })
            viewModel.bottomSheetHideable.observe(viewLifecycleOwner, { bottomSheetHideable ->
                BottomSheetBehavior.from(this).apply {
                    isHideable = bottomSheetHideable
                }
            })
        }
    }

    private fun observeStar() {
        viewModel.starFocused.observe(viewLifecycleOwner, {
            when (it) {
                HomeViewModel.Focus.OnBlackHole -> {
                    binding.ivBlackHole.run {
                        focusStar(this, true)
                    }
                }
                HomeViewModel.Focus.OnPlanet -> {
                    binding.ivMainPlanet.run {
                        focusStar(this, true)
                    }
                }
                HomeViewModel.Focus.OnWhiteHole -> {
                    binding.ivWhiteHole.run {
                        focusStar(this, true)
                    }
                }
                HomeViewModel.Focus.OnSpaceDust -> {
                    binding.ivSpaceDust.run {
                        focusStar(this, true)
                    }
                }
                HomeViewModel.Focus.OffBlackHole -> {
                    binding.ivBlackHole.run {
                        focusStar(this, false)
                    }
                }
                HomeViewModel.Focus.OffPlanet -> {
                    binding.ivMainPlanet.run {
                        focusStar(this, false)
                    }
                }
                HomeViewModel.Focus.OffWhiteHole -> {
                    binding.ivWhiteHole.run {
                        focusStar(this, false)
                    }
                }
                HomeViewModel.Focus.OffSpaceDust -> {
                    binding.ivSpaceDust.run {
                        focusStar(this, false)
                    }
                }
                else -> {
                }
            }
        })
    }

    private fun focusStar(focusedStar: ImageView, isFocusIn: Boolean) {
        val focusedStarXCenter = (focusedStar.left + focusedStar.right) / 2.0f
        val focusedStarYCenter = (focusedStar.top + focusedStar.bottom) / 2.0f
        val focusedStarSize = focusedStar.right - focusedStar.left
        val windowXCenter = Resources.getSystem().displayMetrics.widthPixels / 2.0f
        val windowYCenter = Resources.getSystem().displayMetrics.heightPixels / 2.0f
        val maxStarSize = windowXCenter / MAX_STAR_RATIO
        val scaleSize = if (isFocusIn) maxStarSize / focusedStarSize else 1f
        val focusXMoveSize = if (isFocusIn) (windowXCenter - focusedStarXCenter) else 0f
        val focusYMoveSize = if (isFocusIn) (windowYCenter - focusedStarYCenter) else 0f
        moveStar(focusedStar, scaleSize, focusXMoveSize, focusYMoveSize)
        stars
            .filter { star -> focusedStar != star }
            .forEach { unFocusedStar ->
                val unFocusedStarXCenter = (unFocusedStar.left + unFocusedStar.right) / 2.0f
                val unFocusedStarYCenter = (unFocusedStar.top + unFocusedStar.bottom) / 2.0f
                val unFocusXMoveSize =
                    if (isFocusIn) (focusedStarXCenter - unFocusedStarXCenter) * -STAR_INTERVAL_MAGNIFICATION else 0f
                val unFocusYMoveSize =
                    if (isFocusIn) (focusedStarYCenter - unFocusedStarYCenter) * -STAR_INTERVAL_MAGNIFICATION else 0f
                moveStar(unFocusedStar, scaleSize, unFocusXMoveSize, unFocusYMoveSize)
            }
    }

    private fun moveStar(
        imageView: ImageView,
        scaleSize: Float,
        xMoveSize: Float,
        yMoveSize: Float
    ) {
        val scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", scaleSize)
        val scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", scaleSize)
        val moveY = ObjectAnimator.ofFloat(imageView, "translationY", yMoveSize * 1.0f)
        val moveX = ObjectAnimator.ofFloat(imageView, "translationX", xMoveSize * 1.0f)
        scaleX.duration = STAR_MOVE_TIME
        scaleY.duration = STAR_MOVE_TIME
        moveY.duration = STAR_MOVE_TIME
        moveX.duration = STAR_MOVE_TIME

        val scaleChange = AnimatorSet()
        val move = AnimatorSet()

        scaleChange.play(scaleX).with(scaleY)
        move.play(moveX).with(moveY)
        scaleChange.start()
        move.start()
    }

    companion object {
        const val STAR_MOVE_TIME = 1000L
        const val MAX_STAR_RATIO = 0.6f
        const val STAR_INTERVAL_MAGNIFICATION = 7.0f
    }
}