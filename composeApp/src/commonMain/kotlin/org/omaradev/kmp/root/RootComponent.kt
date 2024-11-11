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
import org.omaradev.kmp.data.model.Picsum
import org.omaradev.kmp.details.DefaultDetailsComponent
import org.omaradev.kmp.list.DefaultListComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, RootComponentChild>>

    fun onBackPressure()
    fun navigateToDetails(item: Picsum)
}

class DefaultRootComponent(
    private val componentContext: ComponentContext,
    private val homeViewModel: HomeViewModel
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootComponentConfiguration>()

    override val childStack: Value<ChildStack<*, RootComponentChild>> = childStack(
        source = navigation,
        serializer = RootComponentConfiguration.serializer(),
        initialConfiguration = RootComponentConfiguration.List,
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    @OptIn(DelicateDecomposeApi::class)
    fun childFactory(
        rootComponentConfiguration: RootComponentConfiguration,
        componentContext: ComponentContext
    ): RootComponentChild {
        return when (rootComponentConfiguration) {
            is RootComponentConfiguration.List -> RootComponentChild.ListChild(
                DefaultListComponent(
                    componentContext = componentContext,
                    homeViewModel = homeViewModel,
                    onClicked = ::navigateToDetails
                )
            )

            is RootComponentConfiguration.Details -> RootComponentChild.DetailsChild(
                DefaultDetailsComponent(
                    componentContext = componentContext,
                    onBack = ::onBackPressure,
                    item = rootComponentConfiguration.item
                )
            )
        }
    }

    override fun onBackPressure() {
        navigation.pop()
    }

    @OptIn(DelicateDecomposeApi::class)
    override fun navigateToDetails(item: Picsum) {
        navigation.push(RootComponentConfiguration.Details(item))
    }

    @Serializable
    sealed interface RootComponentConfiguration {
        @Serializable
        data object List : RootComponentConfiguration

        @Serializable
        data class Details(val item: Picsum) : RootComponentConfiguration
    }
}


