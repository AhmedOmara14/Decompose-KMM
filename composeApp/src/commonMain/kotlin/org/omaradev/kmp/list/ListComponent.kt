package org.omaradev.kmp.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.omaradev.kmp.HomeViewModel
import org.omaradev.kmp.data.model.Product

interface ListComponent {
    val uiState: Value<UIState>

    fun onProductSelected(selectedProduct: Product)

    data class UIState(
        val productList: List<Product>
    )
}

class DefaultListComponent(
    componentContext: ComponentContext,
    private val homeViewModel: HomeViewModel,
    private val onProductSelectedCallback: (selectedProduct: Product, allProducts: List<Product>) -> Unit
) : ListComponent, ComponentContext by componentContext {

    private val _uiState = MutableValue(ListComponent.UIState(emptyList()))
    override val uiState: Value<ListComponent.UIState> = _uiState

    override fun onProductSelected(selectedProduct: Product) {
        onProductSelectedCallback(selectedProduct, _uiState.value.productList)
    }

    init {
        observeProductListUpdates()
    }

    private fun observeProductListUpdates() {
        CoroutineScope(Dispatchers.Default).launch {
            homeViewModel.imagesStatus.collect { products ->
                _uiState.value = ListComponent.UIState(products)
            }
        }
    }
}
