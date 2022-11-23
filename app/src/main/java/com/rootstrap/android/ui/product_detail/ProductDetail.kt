/**
 * Collapsing toolbar based on https://proandroiddev.com/collapsing-toolbar-with-parallax-effect-and-curve-motion-in-jetpack-compose-9ed1c3c0393f
 * and https://medium.com/kotlin-and-kotlin-for-android/collapsing-toolbar-in-jetpack-compose-column-version-11bb2bb83177
 * */
package com.rootstrap.android.ui.product_detail

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.platform.LocalConfiguration
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
import com.rootstrap.android.ui.custom.components.BackArrow
import com.rootstrap.android.ui.custom.components.NewLabel
import com.rootstrap.android.ui.custom.components.PrimaryButton
import com.rootstrap.android.ui.custom.components.RestoredLabel
import com.rootstrap.android.ui.dashboard.DashboardBannerShipment
import com.rootstrap.android.ui.ui.theme.BlackTransparent
import com.rootstrap.android.ui.ui.theme.BoldBody2
import com.rootstrap.android.ui.ui.theme.MaxToolbarHeight
import com.rootstrap.android.ui.ui.theme.PaddingNormal
import com.rootstrap.android.ui.ui.theme.RestoredGreen
import com.rootstrap.android.ui.ui.theme.RichBlack
import com.rootstrap.android.ui.ui.theme.ToolbarHeight
import com.rootstrap.android.ui.ui.theme.ToolbarPaddingStart
import com.rootstrap.domain.Product
import org.koin.androidx.viewmodel.ext.android.getViewModel

private const val titleFontScaleStart = 1f
private const val titleFontScaleEnd = 0.66f
private const val toolbarAnimationDuration = 300

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
fun CollapsingToolbar(product: Product, imageUrl: String, modifier: Modifier = Modifier) {
    val scroll: ScrollState = rememberScrollState(0)
    val headerHeightPx = LocalDensity.current.run { MaxToolbarHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { ToolbarHeight.toPx() }
    var isBoxVisible by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {
        Header(scroll, imageUrl, headerHeightPx)
        ProductBody(product = product, scroll = scroll) {
            isBoxVisible = isBoxVisible.not()
        }
        AnimatedToolbar(scroll, headerHeightPx, toolbarHeightPx)
        Title(
            title = product.name,
            scroll = scroll,
            headerHeightPx = headerHeightPx,
            toolbarHeightPx = toolbarHeightPx
        )
        AnimatedBox(
            modifier = Modifier.align(Alignment.Center),
            isVisible = isBoxVisible,
            background = RestoredGreen,
        )
    }
}

@Composable
private fun Header(
    scroll: ScrollState,
    imageUrl: String,
    headerHeightPx: Float,
) {
    val painter = rememberAsyncImagePainter(imageUrl)
    val imageAlpha = (-1f / headerHeightPx) * scroll.value * 1.20f + 1
    val imageTranslationY = -scroll.value.toFloat() / 2f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaxToolbarHeight)
            .graphicsLayer {
                alpha = imageAlpha
                translationY = imageTranslationY
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
                        colors = listOf(BlackTransparent, Color.Transparent),
                        startY = headerHeightPx,
                        endY = 0f
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

        AnimatedBuyButton(product = product, addToCartClick = addToCartClick)

        // This is to add the collapsing effect of the body
        Spacer(Modifier.height(MaxToolbarHeight * 2))
    }
}

@Composable
fun AnimatedBuyButton(product: Product, addToCartClick: (Product) -> Unit) {

    var isRound by remember {
        mutableStateOf(false)
    }
    val transition = updateTransition(targetState = isRound, label = "transition")

    val background by transition.animateColor(
        transitionSpec = { tween(durationMillis = 500) },
        label = "color"
    ) { isRound ->
        if (isRound) Color.Green else RichBlack
    }

    PrimaryButton<Product>(
        onClick = {
            isRound = isRound.not()
            addToCartClick(product)
        },
        modifier = Modifier
            .padding(PaddingNormal)
            .clip(RoundedCornerShape(8.dp)),
        value = product,
        text = "Buy",
        backgroundColor = background
    )
}

@Composable
fun AnimatedBox(modifier: Modifier, isVisible: Boolean, background: Color) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    val transition = updateTransition(targetState = isVisible, label = "transition")

    val xAxisAnimation by transition.animateDp(transitionSpec = {
        tween(
            delayMillis = 700,
            durationMillis = 500
        )
    }, label = "x") { isVisible ->
        if (isVisible)
            screenWidth.dp
        else 100.dp
    }

    val yAxisAnimation by transition.animateDp(transitionSpec = {
        //spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessMediumLow)
        tween(
            delayMillis = 700,
            durationMillis = 500
        )

    }, label = "x") { isVisible ->
        if (isVisible)
            screenHeight.dp
        else 150.dp
    }

    val borderAnimation by animateIntAsState(
        targetValue = if (isVisible) 50 else 0,
        animationSpec = tween(durationMillis = 800, delayMillis = 500)
    )

    AnimatedVisibility(visible = isVisible) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = modifier
                    .width(xAxisAnimation)
                    .height(yAxisAnimation)
                    .background(background)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(delayMillis = 500)) + expandIn()
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(borderAnimation)
                                )
                                .size(80.dp)
                        ) {}
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Navigate Back",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                Text(
                    text = "Success",
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White,
                    modifier = modifier.padding(top = PaddingNormal)
                )
            }
        }
    }
}

@Composable
private fun AnimatedToolbar(
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(ToolbarHeight)
    ) {
        AnimatedVisibility(
            visible = showToolbar,
            enter = fadeIn(animationSpec = tween(toolbarAnimationDuration)),
            exit = fadeOut(animationSpec = tween(toolbarAnimationDuration))
        ) {
            Box(
                Modifier
                    .background(MaterialTheme.colors.primary)
                    .fillMaxSize()
            )
        }

        TopAppBar(
            navigationIcon = {
                BackArrow(modifier = Modifier)
            },
            title = {},
            backgroundColor = Color.Transparent,
            elevation = 0.dp
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
                    MaxToolbarHeight - titleHeightDp - PaddingNormal,
                    ToolbarHeight / 2 - titleHeightDp / 2,
                    collapseFraction
                )

                val titleX = lerp(
                    PaddingNormal,
                    ToolbarPaddingStart - titleExtraStartPadding,
                    collapseFraction
                )

                translationY = titleY.toPx()
                translationX = titleX.toPx()
                scaleX = scaleXY.value
                scaleY = scaleXY.value
            }
            .onGloballyPositioned {
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

@Preview
@Composable
fun collapsingToolbarPreview() {
    val product = Product(
        description = "Description",
        id = 1,
        name = "Chair",
        price = "40",
        imageUrl = "",
        isRestored = false
    )
    val scroll: ScrollState = rememberScrollState(0)

    CollapsingToolbar(product = product, "")
}
