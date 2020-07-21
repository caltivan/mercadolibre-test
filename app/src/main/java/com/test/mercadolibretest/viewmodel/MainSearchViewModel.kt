package com.test.mercadolibretest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.mercadolibretest.model.MercadoItem
import com.test.mercadolibretest.model.MercadoResponse
import com.test.mercadolibretest.respository.ItemRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * View Model of the MainSearchActivity it will handle the business logic of the View
 */
class MainSearchViewModel(application: Application) : KoinComponent, AndroidViewModel(application) {

    val repository: ItemRepository by inject()
    var result: LiveData<MercadoResponse> = MutableLiveData()
    var items: MutableLiveData<ArrayList<MercadoItem>> = MutableLiveData()
    var tempSearch = String()
    var tempOffset = 0

    init {
        items.value = ArrayList()
    }

    fun startItemSearch(query: String) {
        tempSearch = query
        tempOffset = 0
        result = repository.getMercadoItems(query, 0)
        result.observeForever {
            items.value = it.results
        }
    }

    fun nextPageSearch() {
        val total = result.value!!.paging.total
        val offset = result.value!!.paging.limit
        tempOffset += offset
        tempSearch.let {
            result = repository.getMercadoItems(tempSearch, tempOffset)
            result.observeForever {
                items.value = it.results
                val currentItems = items.value
                val newItems = it.results
                if (currentItems != null) {
                    currentItems.addAll(newItems)
                    items.postValue(currentItems)
                }
            }
        }

    }

    companion object {
        const val BASE_URL = "https://api.mercadolibre.com"

    }
}