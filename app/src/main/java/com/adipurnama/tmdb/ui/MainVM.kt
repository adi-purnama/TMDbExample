package com.adipurnama.tmdb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class MainVM : ViewModel(), KoinComponent {

    /*Bin repository*/
    private val mainRepo by inject<MainRepo> { parametersOf(viewModelScope) }

    /*loading livedata*/
    var showLoading = MutableLiveData<Boolean>()

    /*error even livedate use class event for make sure that only once execution*/
    private val _showError = MutableLiveData<Event<String?>>()
    val showError : LiveData<Event<String?>>
        get() = _showError

    /*List movie livedata*/
    private val _movies=MutableLiveData<List<Movies>>()
    val movies:LiveData<List<Movies>>
    get() = _movies
    /*List movie livedata*/

    /*function for get list movie*/
    fun getMovies(query:String?=null){
        /*set loading*/
        showLoading.value=true
        /*get data from repository with parameter query for search movie by edit_search value*/
        mainRepo.getListMovie(query){success,error->
            showLoading.value=false
            success.let {
                /*get data from repository and update to list movie live data*/
                _movies.value=it
            }
            error.let {
                if (it != null) {
                    /*get error from rest API and set to error livedata*/
                    _showError.value = Event(it)
                }
            }
        }
    }
}