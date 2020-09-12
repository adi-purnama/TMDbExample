package com.adipurnama.tmdb.di

import com.adipurnama.tmdb.repo.MainRepo
import com.adipurnama.tmdb.ui.MainVM
import com.adipurnama.tmdb.ui.detail.DetailVM
import com.adipurnama.tmdb.ui.photos.MoviePhotosVM
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Adi Purnama
 * @2019
 */

val appModules = module {
    /*Inject Repository*/
    factory { (scope: CoroutineScope) -> MainRepo(scope = scope)}

    /*Inject View Model*/
    viewModel { MainVM() }
    viewModel { DetailVM() }
    viewModel { MoviePhotosVM() }
}