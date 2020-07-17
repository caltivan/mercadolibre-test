package com.test.mercadolibretest.model

import com.google.gson.annotations.SerializedName

/**
 * Class tha will store the information of the paging detail on the Mercadolibre API request
 */
data class Paging(

    @SerializedName("total") var total: Int,

    @SerializedName("offset") var offset: Int,

    @SerializedName("limit") var limit: Int,

    @SerializedName("primary_results") var primaryResult: Int


)
