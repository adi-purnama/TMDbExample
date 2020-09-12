package com.adipurnama.tmdb.utilitys

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Created by Adi Purnama
 * @2019
 */
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun SwipeRefreshLayout.stopRefreshing() {
    isRefreshing = false
}

fun EditText.setSearch(result:(String?)->Unit){
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            result(s.toString())
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    })
}