package com.adipurnama.tmdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.adipurnama.tmdb.R
import com.adipurnama.tmdb.utilitys.EventObserver
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainVM:MainVM by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainVM.movieDetail.observe(this,{
            if (it != null) {
                Log.d(">>>MAIN MOVIE", " $it")
            }
        })
        mainVM.showLoading.observe(this,{
            Log.d(">>>LOADING"," $it")
        })
        mainVM.showError.observe(this, EventObserver{
            if (it != null){
                Snackbar.make(parent_main_activity," $it",Snackbar.LENGTH_SHORT).show()
            }
        })
        mainVM.getMoviesDetail("9659")
    }
}