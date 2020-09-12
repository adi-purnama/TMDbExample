package com.adipurnama.tmdb.ui.photos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adipurnama.tmdb.R
import com.adipurnama.tmdb.ui.adapter.MoviePosterAdapter
import com.adipurnama.tmdb.ui.viewPhotos.ImageViewerActivity
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created by Adi Purnama
 * @2019
 */

class PhotosPosterFragment : Fragment() {

    private lateinit var fragmentContext: Context
    private val moviePhotosVM:MoviePhotosVM by sharedViewModel()
    private lateinit var skeletonScreen: SkeletonScreen
    private lateinit var posterRecycleView: RecyclerView
    private lateinit var adapter: MoviePosterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photos_poster, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        posterRecycleView= view.findViewById(R.id.photos_poster)
        adapter= MoviePosterAdapter{
            val intentViewImage= Intent(fragmentContext, ImageViewerActivity::class.java)
            val imageArray= arrayListOf<String>()
            imageArray.add("https://image.tmdb.org/t/p/w500${it.filePath}")
            intentViewImage.putExtra("urls",imageArray)
            intentViewImage.putExtra("page",0)
            startActivity(intentViewImage)
        }
        posterRecycleView.layoutManager = GridLayoutManager(fragmentContext, 2)
        posterRecycleView.adapter=adapter
        showShimmer()
        moviePhotosVM.movieImages.observe(viewLifecycleOwner,{
            if (it.posters != null) {
                skeletonScreen.hide()
                adapter.setListData(it.posters)
            }
        })
    }

    private fun showShimmer(){
        skeletonScreen = Skeleton.bind(posterRecycleView)
            .adapter(adapter)
            .load(R.layout.item_movie_list)
            .show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }
}