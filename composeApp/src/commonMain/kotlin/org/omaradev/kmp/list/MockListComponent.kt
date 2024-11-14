package org.omaradev.kmp.list

import com.arkivanov.decompose.value.MutableValue
import org.omaradev.kmp.data.model.Product

class MockListComponent : ListComponent {
    override val model = MutableValue(ListComponent.Model(items = listOf(
        Product(title = "Sample Product 1", price = 10.0, thumbnail = "https://via.placeholder.com/150"),
        Product(title = "Sample Product 2", price = 20.0, thumbnail = "https://via.placeholder.com/150"),
        Product(title = "Sample Product 3", price = 30.0, thumbnail = "https://via.placeholder.com/150"),
        Product(title = "Sample Product 4", price = 40.0, thumbnail = "https://via.placeholder.com/150")
    )))

    override fun onItemClicked(product: Product) {
        // No-op for preview
    }
}