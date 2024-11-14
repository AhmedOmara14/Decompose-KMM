package org.omaradev.kmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DummyPhotos(
    @SerialName("carts")
    var carts: List<Cart>? = emptyList(),
    @SerialName("limit")
    var limit: Int? = 0,
    @SerialName("skip")
    var skip: Int? = 0,
    @SerialName("total")
    var total: Int? = 0
) {
    fun getAllProducts(): List<Product> {
        val allProducts = mutableListOf<Product>()
        carts?.forEach { cart ->
            cart.products?.filterNotNull()?.let { products ->
                allProducts.addAll(products)
            }
        }
        return allProducts
    }
}
