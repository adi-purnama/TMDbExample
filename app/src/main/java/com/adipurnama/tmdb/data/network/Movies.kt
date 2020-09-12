package com.adipurnama.tmdb.data.network

import com.squareup.moshi.Json

/**
 * Created by Adi Purnama
 * @2019
 */
data class MoviesResult(
    @Json(name="page") val page:Int=0,
    @Json(name="total_results") val totalResults:Int=0,
    @Json(name="total_pages") val totalPages:Int=0,
    @Json(name="results") val results:List<Movies>
)
data class Movies(
    @Json(name="id") val id:Double=0.0,
    @Json(name="title") val title:String="",
    @Json(name="release_date") val releaseDate:String="",
    @Json(name="poster_path") val poster_path:String=""
)