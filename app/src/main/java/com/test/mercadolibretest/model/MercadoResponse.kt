package com.test.mercadolibretest.model

import com.google.gson.annotations.SerializedName
import com.test.mercadolibretest.Paging

data class MercadoResponse(

    @SerializedName("state_id") var stateId: String,

    @SerializedName("query") var query: String,

    @SerializedName("paging") var paging: Paging,

    @SerializedName("results") var results: ArrayList<MercadoItem>

)

