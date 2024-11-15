package org.omaradev.kmp.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import org.omaradev.kmp.data.model.Product

@Composable
fun ProductDetailsScreen(detailsComponent: DetailsComponent) {
    var selectedProduct by remember { mutableStateOf(detailsComponent.model.value.item) }
    val otherProducts = detailsComponent.model.value.AllItems
        .filter { it.title?.contains("Charger") == false && it != selectedProduct }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(245, 245, 245))
    ) {
        AnimatedVisibility(visible = true) {
            ScreenHeader(
                title = "Product Details",
                onBackClick = { detailsComponent.onBackPressure() }
            )
        }

        LazyColumn {
            item {
                ProductDetailsSection(selectedProduct)
                ExpandableProductDescription()

                Divider(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(Color.Gray)
                )
            }

            items(otherProducts.size) { index ->
                ProductListItem(
                    product = otherProducts[index],
                    onProductClick = { selectedProduct = it }
                )
            }
        }
    }
}

@Composable
fun ScreenHeader(title: String, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back Icon",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onBackClick() }
        )
        Text(
            text = title,
            style = TextStyle(
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProductDetailsSection(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProductImageCard(
            imageUrl = product.thumbnail,
            size = 90,
            shape = CircleShape
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = product.title.orEmpty(),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            ProductPriceDisplay(price = product.price)
        }
    }
}

@Composable
fun ProductPriceDisplay(price: Double?) {
    Row {
        Text(
            text = "Price:",
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        )
        Text(
            text = " ${price ?: 0.0} $",
            style = TextStyle(color = Color.Red, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        )
    }
}

@Composable
fun ProductListItem(product: Product, onProductClick: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onProductClick(product) },
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProductImageCard(
                imageUrl = product.thumbnail,
                size = 80,
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = product.title.orEmpty(),
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                ProductDetailsRow(label = "Discount:", value = "${product.discountPercentage} %")
                Spacer(modifier = Modifier.height(4.dp))
                ProductDetailsRow(label = "Quantity:", value = "${product.quantity}")
            }
        }
    }
}

@Composable
fun ProductDetailsRow(label: String, value: String) {
    Row {
        Text(
            text = label,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        )
        Text(
            text = value,
            style = TextStyle(color = Color.Red, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        )
    }
}

@Composable
fun ProductImageCard(imageUrl: String?, size: Int, shape: Shape) {
    Card(
        modifier = Modifier.size(size.dp),
        shape = shape,
        backgroundColor = Color.White,
        elevation = 4.dp
    ) {
        Image(
            painter = rememberImagePainter(imageUrl.orEmpty()),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
fun ExpandableProductDescription() {
    var isExpanded by remember { mutableStateOf(false) }
    val fullDescription = "Stay hydrated and help the planet with our Eco-Friendly Water Bottle! ... [Full Description]"
    val shortDescription = "Stay hydrated and help the planet with our Eco-Friendly Water Bottle! ..."

    Text(
        modifier = Modifier
            .padding(16.dp)
            .clickable { isExpanded = !isExpanded },
        text = buildAnnotatedString {
            append(if (isExpanded) fullDescription else shortDescription)
            pushStyle(SpanStyle(color = Color.Red))
            append(if (isExpanded) " See Less" else " See More")
            pop()
        },
        style = MaterialTheme.typography.body1,
        maxLines = if (isExpanded) Int.MAX_VALUE else 3,
        overflow = TextOverflow.Ellipsis
    )
}
