package org.omaradev.kmp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.omaradev.kmp.data.model.Picsum


@Composable
@Preview
fun App() {
    MaterialTheme {
        AppContent()
    }
}

@Composable
fun AppContent(homeViewModel: HomeViewModel = HomeViewModel()) {
    val images by homeViewModel.imagesStatus.collectAsState()
    BoxWithConstraints {
        val scope = this
        val modifier = Modifier.fillMaxWidth()
        val maxWidth = scope.maxWidth
        var cols = 3
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
                items(images.size) {
                    ImageItem(images[it])
                }
            }
        }
    }
}

@Composable
fun ImageItem(picsum: Picsum) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(16.dp)
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
