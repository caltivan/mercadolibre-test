package com.test.mercadolibretest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.mercadolibretest.model.MercadoItem
import com.test.mercadolibretest.model.Paging
import com.test.mercadolibretest.respository.ItemRepository
import com.test.mercadolibretest.util.MyAppExecutors
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * View Model of the MainSearchActivity it will handle the business logic of the View
 */
class MainSearchViewModel(application: Application) : KoinComponent, AndroidViewModel(application) {

    private val repository: ItemRepository by inject()
    private var tempSearch = String()

    var paging: LiveData<Paging> = MutableLiveData()
    var items: LiveData<ArrayList<MercadoItem>> = MutableLiveData()
    var tempOffset = 0

    init {
        items = repository.getMercadoItems()
        paging = repository.getMercadoPaging()
    }

    fun startItemSearch(query: String) {
        tempSearch = query
        tempOffset = 0
        repository.fetchMercadoItems(query, 0)
    }

    fun nextPageSearch() {
        val offset = paging.value!!.limit
        tempOffset += offset
        tempSearch.let {
            repository.fetchMercadoItems(tempSearch, tempOffset)
        }

    }

    companion object {
        const val BASE_URL = "https://api.mercadolibre.com"

    }
}