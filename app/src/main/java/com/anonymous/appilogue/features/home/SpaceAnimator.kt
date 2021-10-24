package com.anonymous.appilogue.features.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.view.animation.Animation
import android.widget.ImageView

object SpaceAnimator {

    // 홈화면의 배경 space(ImageView)를 좌우반복으로 움직입니다.
    fun animateSpace(space: ImageView) {
        ObjectAnimator.ofFloat(space, TRANSLATION_X, SPACE_MOVE_RANGE).apply {
            duration = SPACE_MOVE_DURATION
            repeatCount = Animation.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }
    }

    /*
    isFocusIn 이 true 일시,
    focusedStar라는 ImageView를 화면의 중심으로 이동시키고 동시에 allStars(List of ImageView)들을 모두 이동시킵니다.
    allStars중 focusedStar를 제외한 ImageView를 각각 unFocusedStar라고 할 때, focusedStar는 중앙으로 이동합니다.
    unFocusedStar가 이동한 방향은 focusedStar에 unFocusedStar로의 방향이며
    unFocusedStar가 이동하는 값은 unFocusedStar의 원래 위치와 focusedStar의 원래 위치의 차이에 STAR_INTERVAL_MAGNIFICATION를 곱한 값과 같습니다.
    focusedStar의 크기는 화면 width의 MAX_STAR_RATIO의 비율로 맞춰지며
    이 때, allStars들의 크기 변화는 focusedStar의 변화값과 동일합니다.

    isFocusIn 이 false 일시,
    시점을 우주 전체를 보는 뷰로 바꾸기 위해 allStars(List of ImageView)들을 모두 원위치로 이동시키고, 크기 또한 원래대로 돌립니다.
     */
    fun focusStar(focusedStar: ImageView, allStars: List<ImageView>, isFocusIn: Boolean) {
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
        allStars
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

    // star(ImageView)를 원하는 위치로 움직입니다.
    private fun moveStar(
        star: ImageView,
        scaleSize: Float,
        xMoveSize: Float,
        yMoveSize: Float
    ) {
        val scaleX = ObjectAnimator.ofFloat(star, SCALE_X, scaleSize)
        val scaleY = ObjectAnimator.ofFloat(star, SCALE_Y, scaleSize)
        val moveX = ObjectAnimator.ofFloat(star, TRANSLATION_X, xMoveSize)
        val moveY = ObjectAnimator.ofFloat(star, TRANSLATION_Y, yMoveSize)
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