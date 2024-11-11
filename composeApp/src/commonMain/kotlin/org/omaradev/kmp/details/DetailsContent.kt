package org.omaradev.kmp.details

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailsContent(component: DetailsComponent) {
    Column {
        Text("Author: ${component.model.value.item.author}")
    }
}
