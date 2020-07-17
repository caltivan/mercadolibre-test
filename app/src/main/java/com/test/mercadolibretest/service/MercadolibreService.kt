package com.test.mercadolibretest.service

import com.test.mercadolibretest.model.MercadoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
* Interface that map the API request to the Mercadolibre API
*/
interface MercadolibreService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("/sites/MLA/search")
    fun searchItem(@Query("q") artist: String): Call<MercadoResponse>


}