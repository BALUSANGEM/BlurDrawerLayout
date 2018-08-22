package org.avantari.blurdrawerlayout

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur

class BlurBuilder {
    private val TAG = BlurBuilder::class.java.simpleName
    private val BITMAP_SCALE = 0.1f
    private val BLUR_RADIUS = 25f

    fun blur(context: Context, image: Bitmap): Bitmap {

        val inputBitmap = Bitmap.createScaledBitmap(image, (image.width * BITMAP_SCALE).toInt(), (image.height * BITMAP_SCALE).toInt(), false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        val rs = RenderScript.create(context)
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
        ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)).apply {
            setRadius(BLUR_RADIUS)
            setInput(tmpIn)
            forEach(tmpOut)
        }

        tmpOut.copyTo(outputBitmap)
        return outputBitmap
    }


}
