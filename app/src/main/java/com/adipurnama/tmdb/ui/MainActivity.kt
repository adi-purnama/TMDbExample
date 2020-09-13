package com.adipurnama.tmdb.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adipurnama.tmdb.R
import com.adipurnama.tmdb.ui.adapter.MoviesAdapter
import com.adipurnama.tmdb.ui.detail.DetailMovieActivity
import com.adipurnama.tmdb.utilitys.EventObserver
import com.adipurnama.tmdb.utilitys.setSearch
import com.adipurnama.tmdb.utilitys.stopRefreshing
import com.adipurnama.tmdb.utilitys.visible
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Adi Purnama
 * @2019
 */

class MainActivity : AppCompatActivity() {

    /*bind view model*/
    private val mainVM:MainVM by viewModel()

    /*local variable*/
    private lateinit var skeletonScreen: SkeletonScreen
    private lateinit var movieRecycleView: RecyclerView
    private lateinit var adapter:MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*set edit view for search movie*/
        edit_search.setSearch {
            mainVM.getMovies(it)
        }
        /*set edit view for search movie*/

        /*set swipe refresh for re fetch data from API*/
        swipe_movie_list.setOnRefreshListener {
            mainVM.getMovies()
            swipe_movie_list.stopRefreshing()
        }
        /*set swipe refresh*/

        /*Initial recycler view Adapter*/
        adapter=MoviesAdapter {
            val intentDetail=Intent(this,DetailMovieActivity::class.java)
            intentDetail.putExtra("detail_id",it.id)
            startActivity(intentDetail)
        }
        movieRecycleView= findViewById(R.id.recycler_movies)
        movieRecycleView.layoutManager = GridLayoutManager(this, 3)
        movieRecycleView.adapter = adapter
        /*Initial recycler view Adapter*/

        showShimmer()
        setupObserve()
    }

    private fun showShimmer(){
        skeletonScreen = Skeleton.bind(movieRecycleView)
            .adapter(adapter)
            .load(R.layout.item_movie_list)
            .show()
    }

    private fun setupObserve(){

        /*observer list movie for set adapter value*/
        mainVM.movies.observe(this,{
            if (it != null) {
                adapter.setListData(it)
                skeletonScreen.hide()
            }
        })

        /*observer loading progress*/
        mainVM.showLoading.observe(this,{
            if (it != null && it){
                skeletonScreen.show()
            }else{
                skeletonScreen.hide()
            }
        })

        /*Observer error event*/
        mainVM.showError.observe(this, EventObserver{
            if (it != null){
                Snackbar.make(parent_main_activity," $it",Snackbar.LENGTH_SHORT).show()
                no_connection.visible()
                skeletonScreen.hide()
            }
        })

        /*get movie from */
        mainVM.getMovies()

    }
}