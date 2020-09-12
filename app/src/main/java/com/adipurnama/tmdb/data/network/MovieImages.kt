package com.adipurnama.tmdb.data.network

import com.squareup.moshi.Json

/**
 * Created by Adi Purnama
 * @2019
 */
data class MovieImages(
    @Json(name="id") val id:Double=0.0,
    @Json(name="backdrops") val backdrops:List<ImagesBackDrop>?,
    @Json(name="posters") val posters:List<ImagesPosters>?
)

data class ImagesBackDrop(
    @Json(name="file_path") val filePath:String=""
)

data class ImagesPosters(
    @Json(name="file_path") val filePath:String=""
)