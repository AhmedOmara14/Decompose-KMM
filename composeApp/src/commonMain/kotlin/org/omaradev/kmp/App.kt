package org.omaradev.kmp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.omaradev.kmp.root.DefaultRootComponent
import org.omaradev.kmp.root.RootComponent
import org.omaradev.kmp.root.RootContent


@Composable
@Preview
fun App(root: RootComponent) {
    MaterialTheme {
        RootContent(root)
    }
}

