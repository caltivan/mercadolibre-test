package com.test.mercadolibretest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.mercadolibretest.model.MercadoItem
import com.test.mercadolibretest.model.MercadoResponse
import com.test.mercadolibretest.service.MercadolibreService
import com.test.mercadolibretest.util.MyAppExecutors
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainSearchViewModel(application: Application) : AndroidViewModel(application) {

    private var mContext = application
    private var retrofit: Retrofit
    var result: MutableLiveData<MercadoResponse> = MutableLiveData()
    var items: MutableLiveData<ArrayList<MercadoItem>> = MutableLiveData()


    init {
        items.value = ArrayList()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun startItemSearch(query: String) {
        MyAppExecutors.instance!!.networkThread.execute {
            val call = retrofit.create(MercadolibreService::class.java).searchItem(query).execute()
            val response = call.body() as MercadoResponse
            result.postValue(response)
            items.postValue(response.results)
        }
    }


    companion object {
        const val BASE_URL = "https://api.mercadolibre.com"

    }
}