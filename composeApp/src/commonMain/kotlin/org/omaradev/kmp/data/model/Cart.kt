package org.omaradev.kmp.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cart(
    @SerialName("discountedTotal")
    var discountedTotal: Double?,
    @SerialName("id")
    var id: Int?,
    @SerialName("products")
    var products: ArrayList<Product?>?,
    @SerialName("total")
    var total: Double?,
    @SerialName("totalProducts")
    var totalProducts: Int?,
    @SerialName("totalQuantity")
    var totalQuantity: Int?,
    @SerialName("userId")
    var userId: Int?
)