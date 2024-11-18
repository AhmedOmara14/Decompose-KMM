package org.omaradev.kmp

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.omaradev.kmp.root.DefaultRootComponent
import org.omaradev.kmp.root.RootComponent
import org.omaradev.kmp.root.RootContent

fun MainViewController(rootComponent: RootComponent) = ComposeUIViewController {
    CompositionLocalProvider {
        RootContent(rootComponent)
    }
}

class LifecycleCallbackImpl : com.arkivanov.essenty.lifecycle.Lifecycle.Callbacks{
    override fun onCreate() {
        super.onCreate()
        println("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("onResume")
    }

}