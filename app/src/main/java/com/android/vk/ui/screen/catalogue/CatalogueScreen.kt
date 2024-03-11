package com.android.vk.ui.screen.catalogue

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.ResourceFont
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.android.vk.R
import com.android.vk.api.fetchData
import com.android.vk.calculateDiscountedPrice
import com.android.vk.data.Product
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun CatalogueScreen(navController: NavHostController) {
    var list by remember { mutableStateOf<List<Product>>(emptyList()) }
    var currentPage by remember { mutableIntStateOf(0) }
    LaunchedEffect(true) {

        list = fetchData(0, 20)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(list) { item ->
                ProductItem(product = item)
                Spacer(
                    modifier = Modifier
                        .size(12.dp)
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp),

                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    IconButton(
                        onClick = {
                            if (currentPage > 1) {
                                currentPage -= 20
                                GlobalScope.launch {
                                    list = fetchData(currentPage, 20)
                                }
                            }
                        },
                        enabled = currentPage > 1
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Confirm",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            currentPage += 20
                            GlobalScope.launch {
                                val nextPage = fetchData(currentPage, 20)
                                if (nextPage.isNotEmpty()) {
                                    list = nextPage
                                } else {
                                }
                            }
                        },
                        enabled = list.size == 20
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Confirm",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(144.dp)
            .padding(4.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CoilImage(image = product.thumbnail)
            Spacer(
                modifier = Modifier
                    .size(12.dp)
            )

            Column {

                Text(
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    text = product.title,
                    fontSize = 20.sp,
                    maxLines = 1,
                    fontFamily = FontFamily(
                        Font(
                            R.font.roboto_regular,
                            FontWeight.Normal,
                            FontStyle.Normal
                        )
                    ),
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.padding(all = 0.dp),
                    text = product.description,
                    fontSize = 12.sp,
                    color = Color(95, 94, 94, 255),
                    maxLines = 2,
                    lineHeight = 14.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.roboto_light,
                            FontWeight.Normal,
                            FontStyle.Normal
                        )
                    ),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(
                    modifier = Modifier
                        .size(12.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row {
                            Text(
                                modifier = Modifier.padding(all = 0.dp),
                                text = "${product.discountPercentage.toInt()}%",
                                fontSize = 12.sp,
                                color = Color(95, 94, 94, 255),
                                maxLines = 2,
                                lineHeight = 14.sp,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.roboto_medium,
                                        FontWeight.Normal,
                                        FontStyle.Normal
                                    )
                                ),
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                modifier = Modifier.padding(all = 0.dp),
                                text = "${product.price}$",
                                fontSize = 12.sp,
                                color = Color(95, 94, 94, 255),
                                maxLines = 2,
                                lineHeight = 14.sp,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.roboto_medium,
                                        FontWeight.Normal,
                                        FontStyle.Normal
                                    )
                                ),
                                overflow = TextOverflow.Ellipsis,
                                style = TextStyle(textDecoration = TextDecoration.LineThrough)
                            )

                        }

                        Text(
                            modifier = Modifier.padding(all = 0.dp),
                            text = "${
                                calculateDiscountedPrice(
                                    product.price.toDouble(),
                                    product.discountPercentage
                                )
                            }$",

                            fontSize = 20.sp,
                            color = Color(194, 0, 0, 255),
                            maxLines = 2,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.roboto_medium,
                                    FontWeight.Normal,
                                    FontStyle.Normal
                                )
                            ),
                            overflow = TextOverflow.Ellipsis
                        )
                    }


                    IconButton(
                        onClick = {

                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Confirm",
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CoilImage(image: String) {
    var imageUrl by remember { mutableStateOf("") }
    imageUrl = image
    Image(
        painter = rememberImagePainter(data = imageUrl),
        contentDescription = null,
        modifier = Modifier
            .size(84.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

