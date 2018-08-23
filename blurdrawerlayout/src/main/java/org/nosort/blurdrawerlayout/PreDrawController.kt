package org.nosort.blurdrawerlayout

import android.view.ViewTreeObserver

interface PreDrawController {
    val onPredrawListener: ViewTreeObserver.OnPreDrawListener
}