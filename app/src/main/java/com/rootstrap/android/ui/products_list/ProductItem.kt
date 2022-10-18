package com.rootstrap.android.ui.products_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.rootstrap.android.R
import com.rootstrap.android.ui.SetContentOnSurface
import com.rootstrap.android.ui.custom.components.PrimaryButton
import com.rootstrap.android.ui.custom.components.RestoredLabel
import com.rootstrap.android.ui.ui.theme.DividerHeight
import com.rootstrap.android.ui.ui.theme.PaddingNormal
import com.rootstrap.domain.Product

@Composable
fun ProductItem(
    modifier: Modifier,
    product: Product,
    addToCartClick: (Product) -> Unit,
) {
    val painter = rememberAsyncImagePainter(product.imageUrl)
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .height(150.dp)
                .padding(16.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(140.dp)
            )

            ProductItemTitleColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp),
                product = product,
                addToCartClick = addToCartClick
            )
        }

        Divider(
            modifier = Modifier
                .height(DividerHeight)
                .padding(horizontal = PaddingNormal)
                .fillMaxWidth(),
            color = Color.LightGray
        )
    }
}

@Composable
fun ProductItemTitleColumn(
    product: Product,
    modifier: Modifier,
    addToCartClick: (Product) -> Unit,
) {
    Column(
        modifier = modifier
    ) {

        // Title
        Text(
            text = product.name,
            style = MaterialTheme.typography.subtitle1
        )

        Divider(Modifier.height(4.dp), color = MaterialTheme.colors.background)

        if (product.isRestored) {
            RestoredLabel()
        }

        Divider(Modifier.height(16.dp), color = MaterialTheme.colors.background)

        Text(
            text = stringResource(id = R.string.price, product.price),
            style = MaterialTheme.typography.h6,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.BottomEnd,
        ) {
            PrimaryButton<Product>(
                onClick = { addToCartClick(product) },
                modifier = Modifier.clip(RoundedCornerShape(4.dp)),
                value = product,
                text = stringResource(R.string.txt_add_to_cart)
            )
        }
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    val product =
        Product(
            description = "",
            id = 1,
            name = "Chair",
            price = "40",
            imageUrl = "https://via.placeholder.com/300.png",
            isRestored = false
        )
    SetContentOnSurface(isDarkTheme = false) {
        ProductItem(
            modifier = Modifier,
            product = product,
        ) {}
    }
}
