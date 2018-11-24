package com.canhhh.kynt.analogfilter.ui.custom

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.Interpolator
import android.widget.Scroller

class NoneSwipeViewPager : ViewPager {
    constructor(context: Context) : super(context) {
        setMyScroller()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setMyScroller()
    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }


    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }


    fun setMyScroller() {
        try {
            val viewpager = ViewPager::class.java
            val scroller = viewpager.getDeclaredField("mScroller")
            scroller.isAccessible = true
            scroller.set(this, MyScroller(context))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    class MyScroller : Scroller {
        constructor(context: Context?) : super(context)
        constructor(context: Context?, interpolator: Interpolator?) : super(context, interpolator)

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
            super.startScroll(startX, startY, dx, dy, 400)
        }
    }

}