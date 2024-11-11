package org.omaradev.kmp

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.omaradev.kmp.root.DefaultRootComponent
import org.omaradev.kmp.root.RootContent

fun MainViewController() = ComposeUIViewController {
    CompositionLocalProvider {
        val root =
            DefaultRootComponent(DefaultComponentContext(LifecycleRegistry()), HomeViewModel())
        RootContent(root)
    }
}