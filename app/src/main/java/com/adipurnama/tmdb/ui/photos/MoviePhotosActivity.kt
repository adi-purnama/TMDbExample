package com.adipurnama.tmdb.ui.photos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adipurnama.tmdb.R
import com.adipurnama.tmdb.ui.adapter.DefaultPagerAdapter
import kotlinx.android.synthetic.main.activity_movie_photos.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Adi Purnama
 * @2019
 */

class MoviePhotosActivity : AppCompatActivity() {
    private val moviePhotosVM:MoviePhotosVM by viewModel()
    private var detailID=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_photos)
        detailID=intent.getDoubleExtra("detail_id",0.0)

        back_menu.setOnClickListener {
            finish()
        }

        val pagerAdapter = DefaultPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(PhotosBackdropFragment(),getString(R.string.backdrop))
        pagerAdapter.addFragment(PhotosPosterFragment(),getString(R.string.posters))
        view_pager_photos.adapter=pagerAdapter
        tab_layout.setupWithViewPager(view_pager_photos)

        moviePhotosVM.getMoviesImages("$detailID")
    }
}