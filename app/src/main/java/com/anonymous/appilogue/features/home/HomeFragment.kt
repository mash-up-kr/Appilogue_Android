package com.anonymous.appilogue.features.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentHomeBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import android.view.animation.Animation

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private val starsByFocus by lazy {
        with(binding) {
            hashMapOf(
                Pair(Focus.OnBlackHole, ivBlackHole),
                Pair(Focus.OffBlackHole, ivBlackHole),
                Pair(Focus.OnWhiteHole, ivWhiteHole),
                Pair(Focus.OffWhiteHole, ivWhiteHole),
                Pair(Focus.OffPlanet, ivMainPlanet),
                Pair(Focus.OnPlanet, ivMainPlanet),
                Pair(Focus.OnSpaceDust, ivSpaceDust),
                Pair(Focus.OffSpaceDust, ivSpaceDust)
            )
        }
    }

    override val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            homeViewModel = viewModel
        }
        initBottomSheet()
        observeStar()
        animateSpace()
    }

    private fun animateSpace() {
        ObjectAnimator.ofFloat(binding.ivSpace, "translationX", SPACE_MOVE_RANGE).apply {
            duration = SPACE_MOVE_DURATION
            repeatCount = Animation.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }
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
                            it.alpha = slideOffset + 1
                        }
                    }
                })
            }
            viewModel.bottomSheetHideable.observe(viewLifecycleOwner, { bottomSheetHideable ->
                BottomSheetBehavior.from(this).apply {
                    isHideable = bottomSheetHideable
                }
            })
        }
    }

    private fun observeStar() {
        viewModel.starFocused.observe(viewLifecycleOwner, {
            when (it.ordinal) {
                in 1..Focus.STAR_NUM -> {
                    starsByFocus[it]?.let { star ->
                        focusStar(star, true)
                    }
                }
                in Focus.STAR_NUM..Focus.STAR_NUM * 2 -> {
                    starsByFocus[it]?.let { star ->
                        focusStar(star, false)
                    }
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
        starsByFocus
            .values
            .toSet()
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
        val moveY = ObjectAnimator.ofFloat(imageView, "translationY", yMoveSize)
        val moveX = ObjectAnimator.ofFloat(imageView, "translationX", xMoveSize)
        listOf(scaleX, scaleY, moveX, moveY).map {
            it.duration = STAR_MOVE_TIME
        }

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
        const val SPACE_MOVE_RANGE = 100f
        const val SPACE_MOVE_DURATION = 6000L
    }
}