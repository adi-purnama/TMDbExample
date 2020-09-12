package com.adipurnama.tmdb.data.model

import com.squareup.moshi.Json

/**
 * Created by Adi Purnama
 * @2019
 */
data class MoviesModel(
    @Json(name="id") val id:Double=0.0,
    @Json(name="title") val title:String="",
    @Json(name="release_date") val releaseDate:String="",
    @Json(name="poster_path") val poster_path:Double=0.0
)