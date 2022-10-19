package com.rootstrap.android.ui.shopping_cart

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.rootstrap.android.R
import com.rootstrap.android.ui.SetContentOnSurface
import com.rootstrap.android.ui.custom.components.PrimaryButton
import com.rootstrap.android.ui.ui.theme.LinkTextSmall
import com.rootstrap.android.ui.ui.theme.PaddingFiveQuarters
import com.rootstrap.android.ui.ui.theme.PaddingSixQuarters
import org.koin.androidx.viewmodel.ext.android.getViewModel

@Composable
fun ShoppingCart() {
    val context = LocalContext.current as ComponentActivity
    val shoppingCartViewModel: ShoppingCartViewModel = context.getViewModel<ShoppingCartViewModel>()
    val uiState: ShoppingCartUiState by shoppingCartViewModel.uiStateFlow.collectAsState()
    LaunchedEffect(key1 = true) {
        shoppingCartViewModel.load()
    }
    ShoppingCart(
        uiState = uiState,
        onClearAllTapped = { shoppingCartViewModel.onClearAllTapped() },
        incrementQuantity = { item: ShoppingCartItem -> shoppingCartViewModel.incrementQuantity(item) },
        decrementQuantity = { item: ShoppingCartItem -> shoppingCartViewModel.decrementQuantity(item) },
        onRemoveItemTapped = { item: ShoppingCartItem ->
            shoppingCartViewModel.onRemoveItemTapped(
                item
            )
        },
        onGoToCheckOutTapped = { shoppingCartViewModel.onGoToCheckOutTapped() }
    )
}

@Composable
fun ShoppingCart(
    uiState: ShoppingCartUiState,
    onClearAllTapped: () -> Unit,
    incrementQuantity: (ShoppingCartItem) -> Unit,
    decrementQuantity: (ShoppingCartItem) -> Unit,
    onRemoveItemTapped: (ShoppingCartItem) -> Unit,
    onGoToCheckOutTapped: () -> Unit,
) {
    SetContentOnSurface {
        Column(
            modifier = Modifier
                .padding(horizontal = PaddingFiveQuarters)
        ) {

            TitleSection {
                onClearAllTapped()
            }

            ShoppingCartList(
                modifier = Modifier.padding(top = PaddingFiveQuarters),
                uiState = uiState,
                incrementQuantity = { incrementQuantity(it) },
                decrementQuantity = { decrementQuantity(it) },
                removeItem = { onRemoveItemTapped(it) }
            )

            TotalSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = PaddingSixQuarters)
                    .weight(1F),
                uiState = uiState,
            ) {
                onGoToCheckOutTapped()
            }
        }
    }
}

@Composable
fun TitleSection(onClearAllTapped: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = PaddingSixQuarters),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(R.string.txt_shopping_cart_title))
        Text(
            text = stringResource(R.string.txt_clear_all),
            style = LinkTextSmall,
            modifier = Modifier.clickable {
                onClearAllTapped()
            }
        )
    }
}

@Composable
fun TotalSection(
    modifier: Modifier,
    uiState: ShoppingCartUiState,
    goToCheckOut: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier,
    ) {

        val (totalText, button) = createRefs()

        Text(
            text = stringResource(id = R.string.txt_total, uiState.total),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(totalText) {
                bottom.linkTo(button.bottom)
                top.linkTo(button.top)
                start.linkTo(parent.start)
            }
        )
        PrimaryButton(
            modifier = Modifier.constrainAs(button) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            value = null,
            text = stringResource(R.string.txt_go_to_checkout),
            onClick = { goToCheckOut() },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingCartList(
    modifier: Modifier,
    uiState: ShoppingCartUiState,
    incrementQuantity: (ShoppingCartItem) -> Unit,
    decrementQuantity: (ShoppingCartItem) -> Unit,
    removeItem: (ShoppingCartItem) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(
            uiState.products,
            key = { pos, key -> uiState.products[pos].product.id }
        ) { _, product ->
            ShoppingCartItem(
                modifier = Modifier.animateItemPlacement(),
                shoppingCartItem = product,
                incrementQuantity = incrementQuantity,
                decrementQuantity = decrementQuantity,
                removeItem = removeItem
            )
        }
    }
}

@Preview
@Composable
fun ShowShoppingCartPreview() {
    ShoppingCart(
        uiState = ShoppingCartUiState(true, emptyList(), 0),
        onClearAllTapped = {},
        incrementQuantity = {},
        decrementQuantity = {},
        onRemoveItemTapped = {},
        onGoToCheckOutTapped = {},
    )
}
