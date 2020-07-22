package com.test.mercadolibretest.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.mercadolibretest.model.MercadoItem
import com.test.mercadolibretest.model.MercadoResponse
import com.test.mercadolibretest.model.Paging
import com.test.mercadolibretest.service.MercadolibreService
import com.test.mercadolibretest.util.MyAppExecutors
import org.koin.core.KoinComponent
import org.koin.core.inject

class ItemRepository : KoinComponent {

    private val mercadoApi: MercadolibreService by inject()

    private var paging: MutableLiveData<Paging> = MutableLiveData()
    private var items: MutableLiveData<ArrayList<MercadoItem>> = MutableLiveData()


    fun getMercadoItems(): LiveData<ArrayList<MercadoItem>> {
        return items
    }

    fun getMercadoPaging(): LiveData<Paging> {
        return paging

    }

    fun fetchMercadoItems(query: String, offset: Int) {
        val dataReceived = mercadoApi.searchItem(query, offset).execute()
        val serverResponse = dataReceived.body() as MercadoResponse
        paging.postValue(serverResponse.paging)
        if (offset != 0) {
            val tempItems = items.value!!
            tempItems.addAll(serverResponse.results)
            items.postValue(tempItems)
        } else {
            items.postValue(serverResponse.results)
        }
    }
}