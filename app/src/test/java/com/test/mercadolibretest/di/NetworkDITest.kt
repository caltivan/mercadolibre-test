package com.test.mercadolibretest.di

import com.test.mercadolibretest.service.MercadolibreService
import com.test.mercadolibretest.viewmodel.MainSearchViewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Network module test configuration with mockserver url.
 */
fun configureNetworkModuleForTest(baseApi: String) = module {
    single {
        Retrofit.Builder().baseUrl(baseApi)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    factory { get<Retrofit>().create(MercadolibreService::class.java) }
}