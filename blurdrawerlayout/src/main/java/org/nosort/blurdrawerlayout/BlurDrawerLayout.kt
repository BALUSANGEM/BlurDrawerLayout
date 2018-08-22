package org.nosort.blurdrawerlayout

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.v4.widget.DrawerLayout
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
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

    private fun getColor(color: Int): Int =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                resources.getColor(color, null)
            } else {
                resources.getColor(color)
            }


    private fun initAll() {
        viewTreeObserver.addOnPreDrawListener(onPredrawListener)
    }

    private lateinit var draggingView: View
    private lateinit var contentView: View
    private val onPredrawListener = object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            if (childCount != 2) {
                throw NotImplementedError("BlurDrawerLayout Should have two childs")
            }
            contentView = getChildAt(0)
            draggingView = getChildAt(1)

            addDrawerListener(drawerListener)
            draggingView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    draggingView.setBackgroundColor(getColor(android.R.color.black))
                    draggingView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
            viewTreeObserver.removeOnPreDrawListener(this)
            return false
        }
    }

    private fun refreshBitmaps() {
        if (backImageCopy == null) {
            createBackgroundBitmap()
        }
        setBackgroundToDraggingView()
    }

    private var drawerListener = object : DrawerListener {
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


    private val contentViewRectangle = Rect()
    private val drageeRect = Rect()
    private fun setBackgroundToDraggingView() =
            try {
                draggingView.getLocalVisibleRect(drageeRect)
                val generateBitmap = generateNewDragBackground(drageeRect)
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

    private fun generateNewDragBackground(rect: Rect): Bitmap {
        val emptyBitmap = emptyDrageeBitmap
        Canvas(emptyBitmap).apply {
            drawBitmap(emptyBitmap, Matrix(), null)
            drawBitmap(backImageCopy, rect.left.toFloat(), rect.top.toFloat(), null)
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

    private fun createBackgroundBitmap() {
        mainContentBitmap = generateBitmap(contentView)
        contentView.getLocalVisibleRect(contentViewRectangle)
        val backImageBitmap = Bitmap.createBitmap(mainContentBitmap, 0, contentViewRectangle.top, draggingView.width, contentViewRectangle.height())
        val blurbackImageCopy = BlurBuilder().blur(context, backImageBitmap)
        backImageCopy = Bitmap.createScaledBitmap(blurbackImageCopy, draggingView.width, draggingView.height, false)
    }
}
