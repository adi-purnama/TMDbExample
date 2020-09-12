package com.adipurnama.tmdb.repo

import com.adipurnama.tmdb.data.network.ApiServiceUrl
import com.adipurnama.tmdb.data.network.MovieDetail
import com.adipurnama.tmdb.data.network.Movies
import com.adipurnama.tmdb.utilitys.request
import kotlinx.coroutines.CoroutineScope
import com.adipurnama.tmdb.utilitys.Result
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by Adi Purnama
 * @2019
 */

class MainRepo(
    private val scope:CoroutineScope
) : KoinComponent {
    private val serviceApi by inject<ApiServiceUrl>()

    fun getListMovie(query:String?=null,response:(List<Movies>?, String?)->Unit){
        request(scope, {
            if (query == null )serviceApi.getMoviesAsync().await() else serviceApi.getMoviesAsync(query).await()
        }, { res ->
            when(res) {
                is Result.Success ->{
                    val result=res.data.results
                    response(result, null)
                }
                is Result.Failure -> response(null, res.error)
            }
        })
    }

    fun getDetailMovie(id:String,response:(MovieDetail?, String?)->Unit){
        request(scope, {serviceApi.getMovieDetailAsync(id).await()
        }, { res ->
            when(res) {
                is Result.Success ->{
                    val result=res.data
                    response(result, null)
                }
                is Result.Failure -> response(null, res.error)
            }
        })
    }
}