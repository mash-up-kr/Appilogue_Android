package com.anonymous.appilogue.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.anonymous.appilogue.R

class FloatingButtonBehavior(
    context: Context,
    attrs: AttributeSet
) : CoordinatorLayout.Behavior<View>(context, attrs) {
    private val bottomSheetPeekHeight = context.resources.getDimension(R.dimen.peek_bottomsheet_home)
    private val margin = context.resources.getDimension(R.dimen.small_margin)

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency.id == R.id.bottomSheetHome
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        child.y =
            if (dependency.y < parent.height - bottomSheetPeekHeight) {
                parent.height - bottomSheetPeekHeight - (child.height + margin)
            } else {
                dependency.y - (child.height + margin)
            }

        return false
    }
}