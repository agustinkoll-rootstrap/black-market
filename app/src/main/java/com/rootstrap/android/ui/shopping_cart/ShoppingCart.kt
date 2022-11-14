package com.rootstrap.android.ui.shopping_cart

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        incrementQuantity = { item: ShoppingCartItemModel -> shoppingCartViewModel.incrementQuantity(item) },
        decrementQuantity = { item: ShoppingCartItemModel -> shoppingCartViewModel.decrementQuantity(item) },
        onRemoveItemTapped = { item: ShoppingCartItemModel ->
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
    incrementQuantity: (ShoppingCartItemModel) -> Unit,
    decrementQuantity: (ShoppingCartItemModel) -> Unit,
    onRemoveItemTapped: (ShoppingCartItemModel) -> Unit,
    onGoToCheckOutTapped: () -> Unit,
) {
    val toolbarHeight = 48.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
// our offset to collapse toolbar
    val toolbarOffsetHeightPx =
        remember { mutableStateOf(0f) }
    val nestedScrollDispatcher = remember { NestedScrollDispatcher() }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // try to consume before LazyColumn to collapse toolbar if needed, hence pre-scroll
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // here's the catch: let's pretend we consumed 0 in any case, since we want
                // LazyColumn to scroll anyway for good UX
                // We're basically watching scroll without taking it
                return Offset.Zero
            }
        }
    }

    SetContentOnSurface {
        Column(
            modifier = Modifier
                .padding(horizontal = PaddingFiveQuarters)
                .nestedScroll(nestedScrollConnection)
        ) {

            TitleSection {
                onClearAllTapped()
            }

            ShoppingCartList(
                modifier = Modifier
                    .padding(top = PaddingFiveQuarters)
                    .weight(1F),
                uiState = uiState,
                incrementQuantity = { incrementQuantity(it) },
                decrementQuantity = { decrementQuantity(it) },
                removeItem = { onRemoveItemTapped(it) }
            )

            TotalSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = PaddingSixQuarters),
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
    incrementQuantity: (ShoppingCartItemModel) -> Unit,
    decrementQuantity: (ShoppingCartItemModel) -> Unit,
    removeItem: (ShoppingCartItemModel) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(
            uiState.products,
            key = { pos, key -> uiState.products[pos].id }
        ) { _, product ->
            ShoppingCartItem(
                modifier = Modifier.animateItemPlacement(),
                shoppingCartItemModel = product,
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
