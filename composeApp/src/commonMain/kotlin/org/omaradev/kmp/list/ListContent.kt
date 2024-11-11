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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import org.omaradev.kmp.data.model.Picsum

@Composable
fun ListContent(
    listComponent: ListComponent
) {
    val images = listComponent.model.subscribeAsState()
    Content(images){
        listComponent.onItemClicked(it)
    }
}

@Composable
fun Content(images: State<ListComponent.Model>,onItemClicked: (picsum: Picsum) -> Unit) {
    var query by remember { mutableStateOf("") }
    BoxWithConstraints {
        val scope = this
        val modifier = Modifier.fillMaxWidth()
        val maxWidth = scope.maxWidth
        var cols = 2
        if (maxWidth > 800.dp) {
            cols = 5
            modifier.widthIn(max = 1080.dp)
        }

        val scrollState = rememberLazyGridState()
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(cols),
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                item(span = {
                    GridItemSpan(cols)
                }) {
                    SearchBar(
                        query = query,
                        onQueryChange = {
                            query = it
                        }
                    )
                }

                items(images.value.items.size) {
                    ImageItem( images.value.items[it]){
                        onItemClicked(it)
                    }
                }
            }
        }
    }
}

@Composable
fun ImageItem(picsum: Picsum, onItemClicked: (picsum: Picsum) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable {
            onItemClicked(picsum)
        },
        elevation = 4.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(16.dp),
    ) {
        Column {
            val painter = rememberImagePainter(picsum.downloadUrl)
            Image(
                modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Black),
                painter = painter,
                contentDescription = "image",
                contentScale = ContentScale.Crop,
            )

            Spacer(Modifier.padding(8.dp))

            picsum.author?.let {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                    ),
                )
            }

            Spacer(Modifier.padding(8.dp))

        }
    }
}


@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String = "Search..."
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, RoundedCornerShape(16.dp))
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(16.dp))
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