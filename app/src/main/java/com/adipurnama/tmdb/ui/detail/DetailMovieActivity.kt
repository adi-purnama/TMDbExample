package com.adipurnama.tmdb.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.adipurnama.tmdb.R
import com.adipurnama.tmdb.data.network.MovieDetail
import com.adipurnama.tmdb.databinding.ActivityMovieDetailBinding
import com.adipurnama.tmdb.ui.photos.MoviePhotosActivity
import com.adipurnama.tmdb.utilitys.EventObserver
import com.adipurnama.tmdb.utilitys.IMAGE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Adi Purnama
 * @2019
 */

class DetailMovieActivity : AppCompatActivity() {
    private val detailVM:DetailVM by viewModel()
    private var detailID=0.0
    private lateinit var skeletonScreen: SkeletonScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding:ActivityMovieDetailBinding= DataBindingUtil.setContentView(this,R.layout.activity_movie_detail)
        binding.lifecycleOwner=this

        detailID=intent.getDoubleExtra("detail_id",0.0)
        if (detailID == 0.0){
            Snackbar.make(parent_movie_detail,"Cannot preview detail", Snackbar.LENGTH_SHORT).show()
            return
        }
        back_menu.setOnClickListener {
            finish()
        }
        showShimmer()

        detailVM.movieDetail.observe(this,{
            if (it != null){
                skeletonScreen.hide()
                binding.data=it
                setView(it)
            }
        })

        detailVM.showLoading.observe(this,{
            if (it != null && it){
                skeletonScreen.show()
            }else{
                skeletonScreen.hide()
            }
        })

        detailVM.showError.observe(this, EventObserver{
            if (it != null){
                Snackbar.make(parent_movie_detail," $it", Snackbar.LENGTH_SHORT).show()
            }
        })
        detailVM.getMoviesDetail(detailID.toString())
    }

    private fun setView(item:MovieDetail){
        if (item.poster_path.isNotEmpty()) {
            Glide.with(this)
                .load("$IMAGE_URL${item.poster_path}")
                .apply(
                    RequestOptions()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .placeholder(R.drawable.ic_image_default)
                .error(R.drawable.ic_image_default)
                .into(image_poster)
        }
        image_view_photos.setOnClickListener {
            val intentPhotos=Intent(this,MoviePhotosActivity::class.java)
            intentPhotos.putExtra("detail_id",detailID)
            startActivity(intentPhotos)
        }
    }

    private fun showShimmer(){
        skeletonScreen = Skeleton.bind(card_detail)
            .load(R.layout.item_shimmer_detail)
            .show()
    }
}