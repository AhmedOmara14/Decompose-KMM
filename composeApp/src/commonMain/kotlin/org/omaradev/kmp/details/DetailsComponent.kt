package org.omaradev.kmp.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import org.omaradev.kmp.data.model.DummyPhotos
import org.omaradev.kmp.data.model.Product

interface DetailsComponent {
    val model: Value<Model>
    fun onBackPressure()

    data class Model(
        var item: Product
    )
}

class DefaultDetailsComponent(
    componentContext: ComponentContext, private val onBack: () -> Unit, item: Product
) : DetailsComponent, ComponentContext by componentContext {
    private var _model = MutableValue(DetailsComponent.Model(item))
    override val model: Value<DetailsComponent.Model> = _model

    override fun onBackPressure() {
        onBack()
    }

}