package org.nosort.blurdrawerlayout

import android.os.Build
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.view.ViewTreeObserver

class PreDrawListener(val drawerLayout: BlurDrawerLayout, drawerListener: DrawerLayout.DrawerListener) {
    private lateinit var draggingView: View
    private lateinit var contentView: View
    private lateinit var blurAlgorithm: BlurAlgorithm
    val onPredrawListener = object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            if (drawerLayout.childCount != 2) {
                throw NotImplementedError("BlurDrawerLayout Should have two childs")
            }
            contentView = drawerLayout.getChildAt(0)
            draggingView = drawerLayout.getChildAt(1)
            blurAlgorithm = DefaultBlurBuilder()
            drawerLayout.addDrawerListener(drawerListener)
            draggingView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    draggingView.setBackgroundColor(getColor(android.R.color.black))
                    draggingView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
            drawerLayout.viewTreeObserver.removeOnPreDrawListener(this)
            return false
        }
    }

    private fun getColor(color: Int): Int =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                drawerLayout.context.resources.getColor(color, null)
            } else {
                drawerLayout.context.resources.getColor(color)
            }
}