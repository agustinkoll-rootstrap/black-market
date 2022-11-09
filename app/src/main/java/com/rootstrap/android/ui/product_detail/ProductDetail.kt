package com.rootstrap.android.ui.product_detail

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.rootstrap.android.R
import com.rootstrap.android.ui.custom.components.NewLabel
import com.rootstrap.android.ui.custom.components.PrimaryButton
import com.rootstrap.android.ui.custom.components.RestoredLabel
import com.rootstrap.android.ui.dashboard.DashboardBannerShipment
import com.rootstrap.android.ui.ui.theme.BoldBody2
import com.rootstrap.android.ui.ui.theme.MaxToolbarHeight
import com.rootstrap.android.ui.ui.theme.PaddingNormal
import com.rootstrap.android.ui.ui.theme.ToolbarHeight
import com.rootstrap.domain.Product
import org.koin.androidx.viewmodel.ext.android.getViewModel

private const val titleFontScaleStart = 1f
private const val titleFontScaleEnd = 0.66f

@Composable
fun ProductDetail(productId: Int) {
    val context = LocalContext.current as ComponentActivity
    val productDetailViewModel: ProductDetailViewModel =
        context.getViewModel<ProductDetailViewModel>()
    val uiState: ProductDetailUiState by productDetailViewModel.uiStateFlow.collectAsState()

    LaunchedEffect(key1 = true) {
        productDetailViewModel.load(productId)
    }

    uiState.product?.run {
        CollapsingToolbar(
            product = this,
            imageUrl = imageUrl,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun CollapsingToolbar(
    product: Product, imageUrl: String, modifier: Modifier = Modifier
) {
    val scroll: ScrollState = rememberScrollState(0)
    val headerHeightPx = LocalDensity.current.run { MaxToolbarHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { ToolbarHeight.toPx() }

    Box(modifier = Modifier.fillMaxSize()) {
        Header(scroll, imageUrl, headerHeightPx)
        ProductBody(product = product, scroll = scroll)
        Toolbar(scroll, headerHeightPx, toolbarHeightPx)
        Title(
            title = product.name,
            scroll = scroll,
            headerHeightPx = headerHeightPx,
            toolbarHeightPx = toolbarHeightPx
        )
    }
}

@Composable
private fun Header(scroll: ScrollState, imageUrl: String, headerHeightPx: Float) {
    val painter = rememberAsyncImagePainter(imageUrl)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaxToolbarHeight)
            .graphicsLayer {
                alpha = (-1f / headerHeightPx) * scroll.value * 1.25f + 1
                translationY = -scroll.value.toFloat() / 2f
            }
    ) {

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize(),
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xAA000000)),
                        startY = 3 * headerHeightPx / 4
                    )
                )
        )
    }
}

@Composable
fun ProductBody(product: Product, scroll: ScrollState, addToCartClick: (Product) -> Unit = {}) {
    Column(
        modifier = Modifier
            .verticalScroll(scroll)
            .fillMaxWidth()
    ) {
        Spacer(Modifier.height(MaxToolbarHeight))
        if (product.isRestored) {
            RestoredLabel(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(PaddingNormal)
            )
        } else {
            NewLabel(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(PaddingNormal)
            )
        }

        Text(
            text = product.description,
            modifier = Modifier.padding(PaddingNormal),
            style = BoldBody2,
        )

        Text(
            text = stringResource(id = R.string.price, product.price),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(PaddingNormal)
        )

        DashboardBannerShipment()

        PrimaryButton<Product>(
            onClick = { addToCartClick(product) },
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .padding(PaddingNormal)
                .align(Alignment.End),
            value = product,
            text = stringResource(R.string.txt_add_to_cart)
        )

        Spacer(Modifier.height(MaxToolbarHeight * 2))
    }
}

@Composable
private fun Toolbar(
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float
) {
    val toolbarBottom = headerHeightPx - toolbarHeightPx
    val showToolbar by remember {
        derivedStateOf {
            scroll.value >= toolbarBottom
        }
    }

    AnimatedVisibility(
        visible = showToolbar,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {

        TopAppBar(
            navigationIcon = {
                BackArrow(modifier = Modifier)
            },
            title = {},
        )
    }
}

@Composable
fun BackArrow(modifier: Modifier) {
    IconButton(
        onClick = {},
        modifier = modifier
            .padding(horizontal = 16.dp)
            .size(36.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "",
            tint = Color.White
        )
    }
}

@Composable
private fun Title(
    title: String,
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float
) {
    var titleHeightPx by remember { mutableStateOf(0f) }
    val titleHeightDp = with(LocalDensity.current) { titleHeightPx.toDp() }
    var titleWidthPx by remember { mutableStateOf(0f) }

    Text(
        text = title,
        modifier = Modifier
            .graphicsLayer {
                val collapseRange: Float = (headerHeightPx - toolbarHeightPx)
                val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)

                val scaleXY = lerp(
                    titleFontScaleStart.dp,
                    titleFontScaleEnd.dp,
                    collapseFraction
                )

                val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 2f

                val titleY = lerp(
                    MaxToolbarHeight - titleHeightDp - PaddingNormal, // start Y
                    ToolbarHeight / 2 - titleHeightDp / 2, // end Y
                    collapseFraction
                )

                val titleX = lerp(
                    PaddingNormal, // start X
                    72.dp - titleExtraStartPadding, // end X
                    collapseFraction
                )

                translationY = titleY.toPx()
                translationX = titleX.toPx()
                scaleX = scaleXY.value
                scaleY = scaleXY.value
            }
            .onGloballyPositioned {
                // We don't know title height in advance to calculate the lerp
                // so we wait for initial composition
                titleHeightPx = it.size.height.toFloat()
                titleWidthPx = it.size.width.toFloat()
            },

        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.White
    )
}

@Preview
@Composable
fun productDetailPreview() {
    val product = Product(
        description = "Description",
        id = 1,
        name = "Chair",
        price = "40",
        imageUrl = "",
        isRestored = false
    )
    val scroll: ScrollState = rememberScrollState(0)

    ProductBody(product = product, scroll) {}
}