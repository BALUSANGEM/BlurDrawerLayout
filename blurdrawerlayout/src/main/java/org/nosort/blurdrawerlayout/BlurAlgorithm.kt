package org.nosort.blurdrawerlayout

import android.content.Context
import android.graphics.Bitmap

interface BlurAlgorithm {
    fun blur(context: Context, image: Bitmap): Bitmap
}