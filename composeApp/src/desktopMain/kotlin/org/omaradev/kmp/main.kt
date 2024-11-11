package org.omaradev.kmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.omaradev.kmp.root.DefaultRootComponent
import org.omaradev.kmp.root.RootContent

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KMM",
    ) {
        val root = DefaultRootComponent(DefaultComponentContext(LifecycleRegistry()), HomeViewModel())
        RootContent(root)
    }
}