package com.adipurnama.tmdb.utilitys

/**
 * Created by Adi Purnama
 * @2019
 */
/*Class for RestApi Response*/
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val error: String?) : Result<Nothing>()
}