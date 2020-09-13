package com.adipurnama.tmdb.di

import android.annotation.SuppressLint
import com.adipurnama.tmdb.data.network.ApiServiceUrl
import com.adipurnama.tmdb.utilitys.API_KEY
import com.adipurnama.tmdb.utilitys.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException

/**
 * Created by Adi Purnama
 * @2019
 */
val remoteModules = module {

    single{
        createWebService<ApiServiceUrl>(
            createHttpClient(),
            BASE_URL
        )
    }

}

/*this wrapper for null value to string*/
object NullToEmptyString {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}

fun createHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        val sockTLSSocketFactory = sslContext.socketFactory

        client.sslSocketFactory(sockTLSSocketFactory, trustAllCerts[0] as X509TrustManager)
        client.hostnameVerifier { _, _ -> true }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    client.readTimeout(30, TimeUnit.SECONDS)
    client.writeTimeout(30, TimeUnit.SECONDS)
    client.connectTimeout(30,TimeUnit.SECONDS)
    client.addInterceptor(provideHttpLoggingInterceptor())
    return client.addInterceptor {
        val original = it.request()
        val url=original.url.newBuilder().addQueryParameter("api_key", API_KEY).build()
        val requestBuilder = original.newBuilder()
        requestBuilder.url(url)
        requestBuilder.header("Content-Type", "application/json")
        val request = requestBuilder.method(original.method, original.body).build()
        return@addInterceptor it.proceed(request)
    }.build()
}

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    baseUrl: String): T {
    val moshi = Moshi.Builder()
        .add(NullToEmptyString)
        .add(KotlinJsonAdapterFactory())
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            MoshiConverterFactory
                .create(moshi)
        )
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}