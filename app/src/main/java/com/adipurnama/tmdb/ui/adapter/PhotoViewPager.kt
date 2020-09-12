package com.adipurnama.tmdb.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by Adi Purnama
 * @2019
 */
class PhotoViewPager (context: Context, attributeSet: AttributeSet): androidx.viewpager.widget.ViewPager(context, attributeSet) {

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return super.onInterceptTouchEvent(ev)
        } catch (ex: java.lang.IllegalArgumentException) {
            ex.printStackTrace()
        }
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return super.onTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }
        return false
    }
}