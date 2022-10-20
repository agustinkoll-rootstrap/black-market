package com.rootstrap.android.ui.shopping_cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.rootstrap.android.R
import com.rootstrap.android.ui.SetContentOnSurface
import com.rootstrap.android.ui.custom.components.NewLabel
import com.rootstrap.android.ui.custom.components.RestoredLabel
import com.rootstrap.android.ui.ui.theme.DividerHeight
import com.rootstrap.android.ui.ui.theme.LinkTextSmall
import com.rootstrap.android.ui.ui.theme.PaddingHalf
import com.rootstrap.android.ui.ui.theme.RoundedCornersRadiusNormal

@Composable
fun ShoppingCartItem(
    modifier: Modifier,
    shoppingCartItemModel: ShoppingCartItemModel,
    incrementQuantity: (ShoppingCartItemModel) -> Unit,
    decrementQuantity: (ShoppingCartItemModel) -> Unit,
    removeItem: (ShoppingCartItemModel) -> Unit,
) {
    val painter = rememberAsyncImagePainter(shoppingCartItemModel.imageUrl)
    Column(
        modifier = modifier
            .border(width = 1.dp, Color.LightGray)
            .background(MaterialTheme.colors.surface)
            .clip(RoundedCornerShape(RoundedCornersRadiusNormal))

    ) {
        Row(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
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
                shoppingCartItemModel = shoppingCartItemModel,
                removeItem = removeItem
            )

            ProductItemPriceColumn(
                shoppingCartItemModel = shoppingCartItemModel,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                incrementQuantity = incrementQuantity,
                decrementQuantity = decrementQuantity
            )
        }

        Divider(
            modifier = Modifier
                .height(DividerHeight)
                .fillMaxWidth(),
            color = Color.LightGray
        )
    }
}

@Composable
fun ProductItemTitleColumn(
    shoppingCartItemModel: ShoppingCartItemModel,
    modifier: Modifier,
    removeItem: (ShoppingCartItemModel) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {

        // Title
        Text(
            text = shoppingCartItemModel.name,
            style = MaterialTheme.typography.subtitle1
        )

        Spacer(Modifier.height(PaddingHalf))

        if (shoppingCartItemModel.isRestored) {
            RestoredLabel()
        } else NewLabel()

        Box(
            modifier = Modifier.weight(1F),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = "Remove",
                style = LinkTextSmall,
                modifier = Modifier.clickable {
                    removeItem(shoppingCartItemModel)
                }
            )
        }
    }
}

@Composable
fun ProductItemPriceColumn(
    shoppingCartItemModel: ShoppingCartItemModel,
    modifier: Modifier,
    incrementQuantity: (ShoppingCartItemModel) -> Unit,
    decrementQuantity: (ShoppingCartItemModel) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.price, shoppingCartItemModel.price),
            style = MaterialTheme.typography.h6,
        )

        IncrementOrDecrementButtons(
            modifier = Modifier,
            shoppingCartItemModel = shoppingCartItemModel,
            incrementQuantity = incrementQuantity,
            decrementQuantity = decrementQuantity
        )
    }
}

@Composable
fun IncrementOrDecrementButtons(
    modifier: Modifier,
    shoppingCartItemModel: ShoppingCartItemModel,
    incrementQuantity: (ShoppingCartItemModel) -> Unit,
    decrementQuantity: (ShoppingCartItemModel) -> Unit,
) {
    Row(modifier.offset(y = (8).dp)) {
        IconButton(onClick = { decrementQuantity(shoppingCartItemModel) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete), "Decrement quantity",
                modifier = Modifier.padding(PaddingHalf)
            )
        }

        Text(
            text = "${shoppingCartItemModel.quantity}",
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )

        IconButton(onClick = { incrementQuantity(shoppingCartItemModel) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add), "Increment quantity",
                modifier = Modifier.padding(PaddingHalf)
            )
        }
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    val shoppingCartItem =
        ShoppingCartItemModel(
            description = "",
            id = 1,
            name = "Chair",
            price = "40",
            imageUrl = "https://via.placeholder.com/300.png",
            isRestored = false
        )

    SetContentOnSurface(isDarkTheme = false) {
        ShoppingCartItem(
            modifier = Modifier,
            shoppingCartItemModel = shoppingCartItem,
            {}, {}, {}
        )
    }
}
