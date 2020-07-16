package com.test.mercadolibretest

import com.google.gson.annotations.SerializedName

data class Paging(

    @SerializedName("total") var total: Int,

    @SerializedName("offset") var offset: Int,

    @SerializedName("limit") var limit: Int,

    @SerializedName("primary_results") var primaryResult: Int


)
