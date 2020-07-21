package com.test.mercadolibretest.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.mercadolibretest.model.MercadoResponse
import com.test.mercadolibretest.service.MercadolibreService
import com.test.mercadolibretest.util.MyAppExecutors
import org.koin.core.KoinComponent
import org.koin.core.inject

class ItemRepository : KoinComponent {

    private val mercadoApi: MercadolibreService by inject()
    private val executor: MyAppExecutors by inject()


    fun getMercadoItems(query: String, offset: Int): LiveData<MercadoResponse> {
        val data = MutableLiveData<MercadoResponse>()
        executor.networkThread.execute {
            val dataReceived = mercadoApi.searchItem(query, offset).execute()
            val serverResponse = dataReceived.body() as MercadoResponse
            data.postValue(serverResponse)
        }
        return data
    }
}