package org.omaradev.kmp.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("discountPercentage")
    var discountPercentage: Double?=null,
    @SerialName("discountedTotal")
    var discountedTotal: Double?=null,
    @SerialName("id")
    var id: Int?=null,
    @SerialName("price")
    var price: Double?=null,
    @SerialName("quantity")
    var quantity: Int?=null,
    @SerialName("thumbnail")
    var thumbnail: String?=null,
    @SerialName("title")
    var title: String?=null,
    @SerialName("total")
    var total: Double?=null
)