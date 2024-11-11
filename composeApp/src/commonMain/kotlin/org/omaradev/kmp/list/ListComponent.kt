package org.omaradev.kmp.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.omaradev.kmp.HomeViewModel
import org.omaradev.kmp.data.model.Picsum

interface ListComponent {
    val model: Value<Model>

    fun onItemClicked(picsum: Picsum)

    data class Model(
        val items: List<Picsum>
    )
}

class DefaultListComponent(
    componentContext: ComponentContext,
    private val homeViewModel: HomeViewModel,
    val onClicked: (picsum: Picsum) -> Unit
) : ListComponent, ComponentContext by componentContext {

    private var _model = MutableValue(ListComponent.Model(emptyList()))
    override val model: Value<ListComponent.Model> = _model

    override fun onItemClicked(picsum: Picsum) {
        onClicked(picsum)
    }

    init {
        CoroutineScope(Dispatchers.Default).launch {
            homeViewModel.imagesStatus.collect {
                _model.value = ListComponent.Model(it)
            }
        }
    }

}