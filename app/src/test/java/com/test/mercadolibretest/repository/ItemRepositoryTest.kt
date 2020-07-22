package com.test.mercadolibretest.repository


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.mercadolibretest.base.BaseUTTest
import com.test.mercadolibretest.di.configureTestAppComponent
import com.test.mercadolibretest.respository.ItemRepository
import com.test.mercadolibretest.service.MercadolibreService
import com.test.mercadolibretest.util.MyAppExecutors
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class ItemRepositoryTest : BaseUTTest() {

    //Target
    private lateinit var mRepo: ItemRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun start() {
        super.setUp()
        startKoin { modules(configureTestAppComponent(getMockWebServerUrl())) }
    }

    @Test
    fun test_paging_fetch_mercadopago_repo_retrieves_expected_data() = runBlocking<Unit> {
        // GIVEN
        mockNetworkResponseWithFileContent("success_resp_list.json", HttpURLConnection.HTTP_OK)
        val query = "item"
        val total = 1
        val limit = 50
        val offset = 0
        // WHEN
        mRepo = ItemRepository()
        mRepo.fetchMercadoItems(query, 0)
        val paging = mRepo.getMercadoPaging()
        //THEN
        assertEquals(paging.value!!.total, total)
        assertEquals(paging.value!!.limit, limit)
        assertEquals(paging.value!!.offset, offset)
    }

    @Test
    fun test_results_fetch_mercadopago_repo_retrieves_expected_data() = runBlocking<Unit> {
        // GIVEN
        mockNetworkResponseWithFileContent("success_resp_list.json", HttpURLConnection.HTTP_OK)
        val query = "item"
        val title = "Cosplay Disfraz Shingeki No Kyojin Capa Mikaza Eren Levi"
        val price = 900
        val total = 1
        // WHEN
        mRepo = ItemRepository()
        mRepo.fetchMercadoItems(query, 0)
        val results = mRepo.getMercadoItems()
        //THEN
        assertEquals(results.value!!.size, total)
        assertEquals(results.value!![0].title, title)
        assertEquals(results.value!![0].price, price.toFloat())
    }

}