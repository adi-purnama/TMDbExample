package com.adipurnama.tmdb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adipurnama.tmdb.data.network.MovieDetail
import com.adipurnama.tmdb.data.network.Movies
import com.adipurnama.tmdb.repo.MainRepo
import com.adipurnama.tmdb.utilitys.Event
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

/**
 * Created by Adi Purnama
 * @2019
 */

class MainVM() : ViewModel(), KoinComponent {
    private val mainRepo by inject<MainRepo> { parametersOf(viewModelScope) }

    var showLoading = MutableLiveData<Boolean>()

    private val _showError = MutableLiveData<Event<String?>>()
    val showError : LiveData<Event<String?>>
        get() = _showError

    private val _movies=MutableLiveData<List<Movies>>()
    val movies:LiveData<List<Movies>>
    get() = _movies
    fun getMovies(query:String?=null){
        showLoading.value=true
        mainRepo.getListMovie(query){success,error->
            success.let {
                _movies.value=it
            }
            error.let {
                if (it != null) {
                    _showError.value = Event(it)
                }
            }
        }
    }

    private val _movieDetail=MutableLiveData<MovieDetail>()
    val movieDetail:LiveData<MovieDetail>
        get() = _movieDetail
    fun getMoviesDetail(id:String){
        showLoading.value=true
        mainRepo.getDetailMovie(id){success,error->
            success.let {
                _movieDetail.value=it
            }
            error.let {
                if (it != null) {
                    _showError.value = Event(it)
                }
            }
        }
    }
}