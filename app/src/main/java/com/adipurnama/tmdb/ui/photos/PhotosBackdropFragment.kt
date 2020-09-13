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
import com.adipurnama.tmdb.ui.adapter.MovieBackdropAdapter
import com.adipurnama.tmdb.ui.viewPhotos.ImageViewerActivity
import com.adipurnama.tmdb.utilitys.EventObserver
import com.adipurnama.tmdb.utilitys.IMAGE_URL
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_photos_backdrop.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created by Adi Purnama
 * @2019
 */

class PhotosBackdropFragment : Fragment() {
    private val moviePhotosVM:MoviePhotosVM by sharedViewModel()
    private lateinit var skeletonScreen: SkeletonScreen
    private lateinit var backdropRecycleView: RecyclerView
    private lateinit var adapter: MovieBackdropAdapter

    private lateinit var fragmentContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photos_backdrop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backdropRecycleView= view.findViewById(R.id.photos_backdrop)
        adapter=MovieBackdropAdapter{
            val intentViewImage=Intent(fragmentContext,ImageViewerActivity::class.java)
            val imageArray= arrayListOf<String>()
            imageArray.add("$IMAGE_URL${it.filePath}")
            intentViewImage.putExtra("urls",imageArray)
            intentViewImage.putExtra("page",0)
            startActivity(intentViewImage)
        }
        backdropRecycleView.layoutManager = GridLayoutManager(fragmentContext, 2)
        backdropRecycleView.adapter=adapter

        showShimmer()
        setObserver()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    private fun showShimmer(){
        skeletonScreen = Skeleton.bind(backdropRecycleView)
            .adapter(adapter)
            .load(R.layout.item_movie_list)
            .show()
    }

    private fun setObserver(){
        moviePhotosVM.movieImages.observe(viewLifecycleOwner,{
            if (it.backdrops != null) {
                skeletonScreen.hide()
                adapter.setListData(it.backdrops)
            }
        })
        moviePhotosVM.showLoading.observe(viewLifecycleOwner,{
            if (it != null && it){
                skeletonScreen.show()
            }else{
                skeletonScreen.hide()
            }
        })
        /*Observer error event*/
        moviePhotosVM.showError.observe(viewLifecycleOwner, EventObserver{
            if (it != null){
                Snackbar.make(parent_photo_backdrop," $it", Snackbar.LENGTH_SHORT).show()
                skeletonScreen.hide()
            }
        })
    }
}