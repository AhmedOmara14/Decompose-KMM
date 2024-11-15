package org.omaradev.kmp.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.seiko.imageloader.rememberImagePainter
import org.omaradev.kmp.data.model.Product

@Composable
fun ListContent(
    listComponent: ListComponent
) {
    val images = listComponent.uiState.subscribeAsState()
    Content(images) {
        listComponent.onProductSelected(it)
    }
}

@Composable
fun Content(images: State<ListComponent.UIState>, onItemClicked: (product: Product) -> Unit) {
    var query by remember { mutableStateOf("") }

    val filteredItems = images.value.productList.filter {
        it.title?.contains("Charger") == false &&
        it.title?.contains(query, ignoreCase = true) == true
    }

    BoxWithConstraints {
        val maxWidth = maxWidth
        val columns = if (maxWidth > 800.dp) 5 else 2
        val scrollState = rememberLazyGridState()

        Column(
            modifier = Modifier.fillMaxWidth()
                .widthIn(max = if (columns == 5) 1080.dp else maxWidth),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                item(span = { GridItemSpan(columns) }) {
                    SearchBar(query, onQueryChange = { query = it })
                }

                items(filteredItems.size) { index ->
                    ImageItem(filteredItems[index], onItemClicked)
                }
            }
        }
    }
}


@Composable
fun ImageItem(product: Product, onItemClicked: (product: Product) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClicked(product) },
        elevation = 4.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(16.dp),
    ) {
        Column {
            val painter = rememberImagePainter(product.thumbnail ?: "")

            Image(
                painter = painter,
                alignment = Alignment.Center,
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
                    .size(130.dp)
                    .background(Color.White)
            )

            Spacer(Modifier.padding(8.dp))

            product.title?.let {
                Text(
                    text = it,
                    maxLines = 2,
                    minLines = 2,
                    textAlign = TextAlign.Center,
                    style = TextStyle(color = Color.Black, fontSize = 16.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }

            product.price?.let {
                Text(
                    text = "$it $",
                    textAlign = TextAlign.Center,
                    style = TextStyle(color = Color.Black, fontSize = 14.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }

            Spacer(Modifier.padding(8.dp))
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, placeholder: String = "Search...") {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp)
    ) {
        BasicTextField(
            value = query,
            onValueChange = {
                onQueryChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp)
        )

        if (query.isEmpty()) {
            Text(
                text = placeholder,
                color = Color.Gray,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


