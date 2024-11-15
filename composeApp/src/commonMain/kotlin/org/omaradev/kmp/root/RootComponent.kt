package org.omaradev.kmp.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.omaradev.kmp.HomeViewModel
import org.omaradev.kmp.data.model.Product
import org.omaradev.kmp.details.DefaultDetailsComponent
import org.omaradev.kmp.list.DefaultListComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, RootChild>>

    fun handleBackPress()

    fun navigateToDetails(selectedProduct: Product, allProducts: List<Product>)
}

class DefaultRootComponent(
    private val componentContext: ComponentContext,
    private val homeViewModel: HomeViewModel
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootConfiguration>()

    override val childStack: Value<ChildStack<*, RootChild>> = childStack(
        source = navigation,
        serializer = RootConfiguration.serializer(),
        initialConfiguration = RootConfiguration.ProductList,
        handleBackButton = true,
        childFactory = ::createChild,
    )

    /**
     * Factory method to create child components based on the configuration.
     */
    private fun createChild(
        configuration: RootConfiguration,
        componentContext: ComponentContext
    ): RootChild = when (configuration) {
        is RootConfiguration.ProductList -> RootChild.ProductListChild(
            DefaultListComponent(
                componentContext = componentContext,
                homeViewModel = homeViewModel,
                onProductSelectedCallback = ::navigateToDetails
            )
        )
        is RootConfiguration.ProductDetails -> RootChild.ProductDetailsChild(
            DefaultDetailsComponent(
                componentContext = componentContext,
                onBack = ::handleBackPress,
                item = configuration.selectedProduct,
                items = configuration.allProducts
            )
        )
    }

    /**
     * Handles back navigation in the component stack.
     */
    override fun handleBackPress() {
        navigation.pop()
    }

    /**
     * Navigates to the product details screen.
     */
    @OptIn(DelicateDecomposeApi::class)
    override fun navigateToDetails(selectedProduct: Product, allProducts: List<Product>) {
        navigation.push(RootConfiguration.ProductDetails(selectedProduct, allProducts))
    }

    /**
     * Sealed class for representing navigation configurations.
     */
    @Serializable
    sealed interface RootConfiguration {
        @Serializable
        object ProductList : RootConfiguration

        @Serializable
        data class ProductDetails(
            val selectedProduct: Product,
            val allProducts: List<Product>
        ) : RootConfiguration
    }
}

/**
 * Sealed class for child components in the root stack.
 */
sealed interface RootChild {
    data class ProductListChild(val component: DefaultListComponent) : RootChild
    data class ProductDetailsChild(val component: DefaultDetailsComponent) : RootChild
}
