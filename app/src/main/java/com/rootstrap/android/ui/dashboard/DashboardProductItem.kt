package com.rootstrap.android.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.rootstrap.android.R
import com.rootstrap.android.ui.SetContentOnSurface
import com.rootstrap.android.ui.custom.components.NewLabel
import com.rootstrap.android.ui.custom.components.RestoredLabel
import com.rootstrap.android.ui.ui.theme.BoldBody2
import com.rootstrap.android.ui.ui.theme.DividerHeight
import com.rootstrap.android.ui.ui.theme.PaddingHalf
import com.rootstrap.android.ui.ui.theme.PaddingThreeQuarters
import com.rootstrap.android.ui.ui.theme.ProductItemDashboardHeight
import com.rootstrap.android.ui.ui.theme.ProductItemDashboardWidth
import com.rootstrap.android.ui.ui.theme.elevationNormal
import com.rootstrap.android.ui.ui.theme.roundedCornersRadiusNormal
import com.rootstrap.domain.Product

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardProductItem(
    modifier: Modifier,
    product: Product,
    addToFavourites: (Product) -> Unit,
    openDetail: (Product) -> Unit
) {
    val painter = rememberAsyncImagePainter(product.imageUrl)

    Card(
        modifier = modifier
            .padding(PaddingHalf)
            .width(ProductItemDashboardWidth)
            .clip(RoundedCornerShape(roundedCornersRadiusNormal)),
        elevation = elevationNormal,
        onClick = { openDetail(product) }
    ) {
        Column {
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.txt_product_image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ProductItemDashboardHeight)
            )

            Divider(
                modifier = Modifier
                    .height(DividerHeight)
                    .fillMaxWidth(),
                color = Color.LightGray
            )

            FirstRow(product)
            SecondRow(product, addToFavourites)
        }
    }
}

@Composable
fun FirstRow(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = PaddingThreeQuarters)
            .padding(horizontal = PaddingHalf),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.price, product.price),
            style = BoldBody2
        )

        if (product.isRestored) {
            RestoredLabel()
        } else {
            NewLabel()
        }
    }
}

@Composable
fun SecondRow(product: Product, addToFavourites: (Product) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PaddingHalf),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = product.name, style = BoldBody2)

        IconButton(onClick = { addToFavourites(product) }) {
            Icon(
                painter = painterResource(R.drawable.ic_favorite),
                contentDescription = "Add to favourites"
            )
        }
    }
}

@Preview(widthDp = 136, heightDp = 250)
@Composable
fun ListItemPreview() {
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
        DashboardProductItem(
            modifier = Modifier,
            product = product,
            {}, {}
        )
    }
}
