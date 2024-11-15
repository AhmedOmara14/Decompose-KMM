package org.omaradev.kmp.root
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import org.omaradev.kmp.details.ProductDetailsScreen
import org.omaradev.kmp.list.ListContent


@Composable
fun RootContent(
    component: RootComponent
) {
    Children(
        stack = component.childStack,
        modifier = Modifier.fillMaxSize(),
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is RootChild.ProductListChild -> ListContent(child.component)
            is RootChild.ProductDetailsChild -> ProductDetailsScreen(child.component)
        }
    }
}

