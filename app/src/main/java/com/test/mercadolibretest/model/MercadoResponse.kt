package com.test.mercadolibretest.model

import com.google.gson.annotations.SerializedName

/**
* Class tha will store the information of the response on the Mercadolibre API
*/
data class MercadoResponse(

    @SerializedName("state_id") var stateId: String,

    @SerializedName("query") var query: String,

    @SerializedName("paging") var paging: Paging,

    @SerializedName("results") var results: ArrayList<MercadoItem>

)

