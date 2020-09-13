package com.adipurnama.tmdb.utilitys

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okio.IOException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

/**
 * Created by Adi Purnama
 * @2019
 */

/*Class for request and handling response restAPI*/
fun <T> request(coroutineScope: CoroutineScope,
                response: suspend() -> Response<T>,
                results:(Result<T>) -> Unit) {
    coroutineScope.launch {
        handlingResponse(response) { results(it) }
    }
}

/*this function use coroutine for non blocking thread*/
suspend fun <T> handlingResponse(response: suspend() -> Response<T>,
                                 results:(Result<T>) -> Unit) {
    val stringFailure="Failed to connect to server"
    try {
        val result = response()
        if (result.isSuccessful) {
            result.body()?.let { body ->
                if (result.code() == 500) {
                    /*server send internal error*/
                    results(Result.Failure(stringFailure))
                } else {
                    results(Result.Success(body))
                }
            }
        } else {
            /*get error message from response*/
            var bodyError=stringFailure
            if (result.code()!=500) {
                try {
                    val message = JSONObject(result.errorBody()?.string()?:"")
                    if (message.has("message")) bodyError = message.getString("message")
                } catch (ex: JSONException) {
                    Log.e(">>>API NOT JSON ", " $ex")
                    results(Result.Failure(bodyError))
                }
            }
            Log.e(">>>API ERROR"," ${result.errorBody()?.contentType()}")
            results(Result.Failure(bodyError))
        }
    } catch (throwable: Throwable) {
        Log.e(">>>API THROW", throwable.toString())
        results(Result.Failure(stringFailure))
    } catch (networkError: IOException) {
        Log.e(">>>API Network"," $networkError")
        results(Result.Failure(stringFailure))
    }
}