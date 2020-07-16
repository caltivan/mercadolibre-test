package com.test.mercadolibretest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.test.mercadolibretest.model.MercadoItem
import com.test.mercadolibretest.model.MercadoResponse
import com.test.mercadolibretest.service.MercadolibreService
import com.test.mercadolibretest.util.MyAppExecutors
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainSearchViewModel(application: Application) : AndroidViewModel(application) {
    private var mContext = application

    private val BASE_URL = "https://api.mercadolibre.com"
    private var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun startItemSearch(query: String) {
        MyAppExecutors.instance!!.networkThread.execute {
            val call = retrofit.create(MercadolibreService::class.java).searchItem(query).execute()
            val item = call.body() as MercadoResponse

            val s = item.query
        }

    }

}