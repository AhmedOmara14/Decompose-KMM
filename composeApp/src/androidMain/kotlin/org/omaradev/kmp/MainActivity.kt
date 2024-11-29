package org.omaradev.kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.omaradev.kmp.root.DefaultRootComponent
import org.omaradev.kmp.root.RootComponent

class MainActivity : ComponentActivity() {

    private val modules = module {
        single<ComponentContext> { defaultComponentContext() }
    }

    init {
        loadKoinModules(modules)
    }

    private val rootComponent: RootComponent by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(rootComponent)
        }
    }
}
