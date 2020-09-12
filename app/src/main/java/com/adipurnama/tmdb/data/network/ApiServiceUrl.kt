package com.adipurnama.tmdb.data.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Adi Purnama
 * @2019
 */

interface ApiServiceUrl {
    @GET("discover/movie")
    fun getMoviesAsync(): Deferred<Response<MoviesResult>>

    @GET("search/movie/")
    fun getMoviesAsync(
        @Query("query") query: String): Deferred<Response<MoviesResult>>

    @GET("movie/{id}")
    fun getMovieDetailAsync(
        @Path("id") id: String): Deferred<Response<MovieDetail>>

    @GET("movie/{id}/images")
    fun getMovieImagesAsync(
        @Path("id") id: String): Deferred<Response<MovieDetail>>
}