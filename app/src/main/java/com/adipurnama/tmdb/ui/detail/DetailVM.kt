package com.adipurnama.tmdb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adipurnama.tmdb.data.network.MovieDetail
import com.adipurnama.tmdb.repo.MainRepo
import com.adipurnama.tmdb.utilitys.Event
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

/**
 * Created by Adi Purnama
 * @2019
 */

class DetailVM : ViewModel(), KoinComponent {
    private val mainRepo by inject<MainRepo> { parametersOf(viewModelScope) }

    var showLoading = MutableLiveData<Boolean>()

    private val _showError = MutableLiveData<Event<String?>>()
    val showError : LiveData<Event<String?>>
        get() = _showError

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