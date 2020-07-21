package com.test.mercadolibretest.di

import com.test.mercadolibretest.service.MercadolibreService
import com.test.mercadolibretest.util.MyAppExecutors
import com.test.mercadolibretest.viewmodel.MainSearchViewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Network dependency module.
 * Provides Retrofit dependency with OkHttp logger.
 */
val networkDependency = module {

    single {
        Retrofit.Builder().baseUrl(MainSearchViewModel.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    }
    factory { get<Retrofit>().create(MercadolibreService::class.java) }
}