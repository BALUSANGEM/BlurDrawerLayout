package org.nosort.blurdrawerlayout

import android.support.v4.widget.DrawerLayout
import android.view.ViewTreeObserver

class DefaultPreDrawController(val drawerLayout: BlurDrawerLayout, drawerListener: DrawerLayout.DrawerListener) : PreDrawController {
    override val onPredrawListener = object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            if (drawerLayout.childCount != 2) {
                throw NotImplementedError("BlurDrawerLayout Should have two childs")
            }
            drawerLayout.addDrawerListener(drawerListener)
            drawerLayout.viewTreeObserver.removeOnPreDrawListener(this)
            return false
        }
    }
}