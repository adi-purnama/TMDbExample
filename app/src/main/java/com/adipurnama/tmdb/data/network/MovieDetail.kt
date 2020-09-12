package com.adipurnama.tmdb.data.network

import com.squareup.moshi.Json

/**
 * Created by Adi Purnama
 * @2019
 */

data class MovieDetail(
    @Json(name="id") val id:Double=0.0,
    @Json(name="poster_path") val poster_path:String="",
    @Json(name="title") val title:String="",
    @Json(name="release_date") val releaseDate:String="",
    @Json(name="homepage") val homepage:String="",
    @Json(name="production_companies") val productionCompanies:List<ProductionCompanies>?,
    @Json(name="production_string") var productionString:String="",
    @Json(name="tagline") val tagLine:String=""
)

data class ProductionCompanies(
    @Json(name="id") val id:Double=0.0,
    @Json(name="name") val name:String=""
)