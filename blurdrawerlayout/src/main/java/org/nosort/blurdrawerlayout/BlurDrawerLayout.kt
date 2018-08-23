package org.nosort.blurdrawerlayout

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ScrollView


class BlurDrawerLayout : DrawerLayout {
    val TAG = BlurDrawerLayout::class.java.simpleName

    constructor(context: Context) : super(context) {
        initAll()
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        initAll()
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        initAll()
    }

    private val drawerListener = object : DrawerListener {
        override fun onDrawerStateChanged(state: Int) =
                when (state) {
                    DrawerLayout.STATE_DRAGGING -> refreshBitmaps()
                    DrawerLayout.STATE_SETTLING -> refreshBitmaps()
                    else -> {
                    }
                }

        override fun onDrawerSlide(p0: View, p1: Float) = refreshBitmaps()

        override fun onDrawerClosed(p0: View) {
            backImageCopy = null
        }

        override fun onDrawerOpened(p0: View) {
        }
    }

    private val preDrawController: PreDrawController = DefaultPreDrawController(this, drawerListener)
    private var blurAlgorithm: BlurAlgorithm = DefaultBlurBuilder()

    private fun initAll() {
        viewTreeObserver.addOnPreDrawListener(preDrawController.onPredrawListener)
    }

    private fun refreshBitmaps() {
        val layoutParams = draggingView.layoutParams as DrawerLayout.LayoutParams
        val isStart = layoutParams.gravity == GravityCompat.START
        if (backImageCopy == null) {
            createBackgroundBitmap(isStart)
        }
        setBackgroundToDraggingView(isStart)

    }


    private val draggingView by lazy {
        getChildAt(1)
    }

    private val contentView by lazy {
        getChildAt(0)
    }


    private val contentViewRectangle = Rect()
    private val drageeRect = Rect()
    private fun setBackgroundToDraggingView(isStart: Boolean) =
            try {
                draggingView.getLocalVisibleRect(drageeRect)
                Log.d(TAG, "setBackgroundToDraggingView drageeRect:${drageeRect.flattenToString()}")
                val generateBitmap = generateNewDragBackground(drageeRect, isStart)
                draggingView.background = BitmapDrawable(resources, generateBitmap)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }

    private val emptyDrageeBitmap by lazy {
        Bitmap.createBitmap(draggingView.width, draggingView.height, Bitmap.Config.ARGB_8888)
    }

    private var backImageCopy: Bitmap? = null
    private val transprentWhiteColor by lazy {
        Color.argb(95, 255, 255, 255)
    }

    private fun generateNewDragBackground(rect: Rect, isStart: Boolean): Bitmap {
        val emptyBitmap = emptyDrageeBitmap
        Canvas(emptyBitmap).apply {
            drawBitmap(emptyBitmap, Matrix(), null)
            if (isStart) {
                drawBitmap(backImageCopy, rect.left.toFloat(), rect.top.toFloat(), null)
            } else {
                drawBitmap(backImageCopy, rect.right.toFloat() - emptyBitmap.width, rect.top.toFloat(), null)
            }
            drawColor(transprentWhiteColor)
        }
        return emptyBitmap
    }

    private fun generateBitmap(v: View) =
            if (v is ScrollView) {
                val childView = v.getChildAt(0)
                val totalWidth = childView.width
                val totalHeight = childView.height
                val returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(returnedBitmap)
                val bgDrawable = v.getBackground()
                if (bgDrawable != null)
                    bgDrawable.draw(canvas)
                else
                    canvas.drawColor(Color.WHITE)
                v.draw(canvas)
                returnedBitmap
            } else {
                val returnedBitmap = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
                val c = Canvas(returnedBitmap)
                v.layout(v.left, v.top, v.right, v.bottom)
                v.draw(c)
                returnedBitmap
            }

    private lateinit var mainContentBitmap: Bitmap

    private fun createBackgroundBitmap(isStart: Boolean) {
        mainContentBitmap = generateBitmap(contentView)
        contentView.getLocalVisibleRect(contentViewRectangle)
        val backImageBitmap =
                if (isStart)
                    Bitmap.createBitmap(mainContentBitmap, 0, contentViewRectangle.top, draggingView.width, contentViewRectangle.height())
                else
                    Bitmap.createBitmap(mainContentBitmap, contentViewRectangle.right - draggingView.width, contentViewRectangle.top, draggingView.width, contentViewRectangle.height())

        val blurbackImageCopy = blurAlgorithm.blur(context, backImageBitmap)
        backImageCopy = Bitmap.createScaledBitmap(blurbackImageCopy, draggingView.width, draggingView.height, false)
    }

    fun setBlurAlgorithm(blurAlgorithm: BlurAlgorithm) {
        this.blurAlgorithm = blurAlgorithm
    }
}
