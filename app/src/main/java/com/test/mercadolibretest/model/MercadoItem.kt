package com.test.mercadolibretest.model

import com.google.gson.annotations.SerializedName

data class MercadoItem(

    @SerializedName("id") var id: String,

    @SerializedName("title") var title: String,

    @SerializedName("price") var price: Integer,

    @SerializedName("currency_id") var currencyId: String,

    @SerializedName("available_quantity") var availableQuantity: Int,

    @SerializedName("sold_quantity") var soldQuantity: Int,

    @SerializedName("accepts_mercadopago") var acceptsMercadopago: Boolean,

    @SerializedName("condition") var condition: String = String(),

    @SerializedName("permalink") var permalink: String = String(),

    @SerializedName("thumbnail") var thumbnail: String = String()

)

