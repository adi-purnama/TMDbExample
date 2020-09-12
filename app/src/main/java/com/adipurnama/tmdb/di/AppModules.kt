package com.adipurnama.tmdb.di

import com.adipurnama.tmdb.data.network.ApiServiceUrl
import com.adipurnama.tmdb.repo.MainRepo
import com.adipurnama.tmdb.ui.MainVM
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
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
}