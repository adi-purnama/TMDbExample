package com.adipurnama.tmdb.ui.viewPhotos

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.adipurnama.tmdb.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.chrisbanes.photoview.PhotoView
import kotlinx.android.synthetic.main.activity_image_viewer.*

/**
 * Created by Adi Purnama
 * @2019
 */
class ImageViewerActivity : AppCompatActivity() {
    lateinit var bannerUrls: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)
        back_menu.setOnClickListener { finish() }
        if (!intent.hasExtra("urls")){
            Toast.makeText(this,"Cannot Preview Image",Toast.LENGTH_LONG).show()
            finish()
        }
        bannerUrls = intent.getStringArrayListExtra("urls") as ArrayList<String>
        photo_viewer.adapter = ImageAdapter()
        photo_viewer.currentItem = intent.getIntExtra("page", 0)
    }

    internal inner class ImageAdapter : PagerAdapter() {
        override fun getCount(): Int {
            return bannerUrls.size
        }
        override fun isViewFromObject(v: View, obj: Any): Boolean {
            return v === obj as PhotoView
        }
        override fun instantiateItem(container: ViewGroup, i: Int): Any {
            val photo = PhotoView(this@ImageViewerActivity)
            Glide.with(this@ImageViewerActivity).load(bannerUrls[i])
                .apply(RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_image_default)
                    .error(R.drawable.ic_image_default))
                .into(photo)
            (container as ViewPager).addView(photo, 0)
            return photo
        }

        override fun destroyItem(container: ViewGroup, i: Int, obj: Any) {
            (container as ViewPager).removeView(obj as PhotoView)
        }
    }
}