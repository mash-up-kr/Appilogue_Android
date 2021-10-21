package com.anonymous.appilogue.features.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.view.animation.Animation
import android.widget.ImageView

class SpaceStateManager(private val stars: List<ImageView>) {

    fun animateSpace(space: ImageView) {
        ObjectAnimator.ofFloat(space, TRANSLATION_X, SPACE_MOVE_RANGE).apply {
            duration = SPACE_MOVE_DURATION
            repeatCount = Animation.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }
    }

    fun focusStar(focusedStar: ImageView, isFocusIn: Boolean) {
        val focusedStarXCenter = (focusedStar.left + focusedStar.right) / 2.0f
        val focusedStarYCenter = (focusedStar.top + focusedStar.bottom) / 2.0f
        val focusedStarSize = focusedStar.right - focusedStar.left
        val windowXCenter = Resources.getSystem().displayMetrics.widthPixels / 2.0F
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
        val scaleX = ObjectAnimator.ofFloat(imageView, SCALE_X, scaleSize)
        val scaleY = ObjectAnimator.ofFloat(imageView, SCALE_Y, scaleSize)
        val moveX = ObjectAnimator.ofFloat(imageView, TRANSLATION_X, xMoveSize)
        val moveY = ObjectAnimator.ofFloat(imageView, TRANSLATION_Y, yMoveSize)
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
        private const val SCALE_X = "scaleX"
        private const val SCALE_Y = "scaleY"
        private const val TRANSLATION_X = "translationX"
        private const val TRANSLATION_Y = "translationY"
        private const val STAR_MOVE_TIME = 1000L
        private const val MAX_STAR_RATIO = 0.6f
        private const val STAR_INTERVAL_MAGNIFICATION = 7.0f
        private const val SPACE_MOVE_RANGE = 100f
        private const val SPACE_MOVE_DURATION = 6000L
    }
}