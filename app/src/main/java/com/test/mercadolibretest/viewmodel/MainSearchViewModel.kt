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

/**
 * View Model of the MainSearchActivity it will handle the business logic of the View
 */
class MainSearchViewModel(application: Application) : AndroidViewModel(application) {

    private var mContext = application
    private var retrofit: Retrofit
    var result: MutableLiveData<MercadoResponse> = MutableLiveData()
    var items: MutableLiveData<ArrayList<MercadoItem>> = MutableLiveData()
    var tempSearch = String()
    var tempOffset = 0

    init {
        items.value = ArrayList()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun startItemSearch(query: String) {
        tempSearch = query
        tempOffset = 0
        MyAppExecutors.instance!!.networkThread.execute {
            val call =
                retrofit.create(MercadolibreService::class.java).searchItem(query, 0).execute()
            val response = call.body() as MercadoResponse
            result.postValue(response)
            items.postValue(response.results)
        }
    }

    fun nextPageSearch() {
        val total = result.value!!.paging.total
        val offset = result.value!!.paging.limit
        tempOffset += offset
        tempSearch.let {
            MyAppExecutors.instance!!.networkThread.execute {
                val call =
                    retrofit.create(MercadolibreService::class.java)
                        .searchItem(tempSearch, tempOffset)
                        .execute()
                val response = call.body() as MercadoResponse
                result.postValue(response)
                // items.postValue(response.results)
                val list = items.value
                list!!.addAll(response.results)
                items.postValue(list)
            }
        }

    }

    companion object {
        const val BASE_URL = "https://api.mercadolibre.com"

    }
}