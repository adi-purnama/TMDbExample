package com.adipurnama.tmdb.ui.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adipurnama.tmdb.data.network.MovieImages
import com.adipurnama.tmdb.repo.MainRepo
import com.adipurnama.tmdb.utilitys.Event
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

/**
 * Created by Adi Purnama
 * @2019
 */
class MoviePhotosVM :ViewModel(),KoinComponent{
    private val mainRepo by inject<MainRepo> { parametersOf(viewModelScope) }

    var showLoading = MutableLiveData<Boolean>()

    private val _showError = MutableLiveData<Event<String?>>()
    val showError : LiveData<Event<String?>>
        get() = _showError

    private val _movieImages= MutableLiveData<MovieImages>()
    val movieImages: LiveData<MovieImages>
        get() = _movieImages
    fun getMoviesImages(id:String){
        showLoading.value=true
        mainRepo.getMovieImages(id){success,error->
            success.let {
                _movieImages.value=it
            }
            error.let {
                if (it != null) {
                    _showError.value = Event(it)
                }
            }
        }
    }
}