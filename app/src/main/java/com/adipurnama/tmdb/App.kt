package com.adipurnama.tmdb

import android.app.Application
import com.adipurnama.tmdb.di.appModules
import com.adipurnama.tmdb.di.remoteModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
/**
 * Created by Adi Purnama
 * @2019
 */

@Suppress("unused")
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(remoteModules, appModules))
        }
    }
}