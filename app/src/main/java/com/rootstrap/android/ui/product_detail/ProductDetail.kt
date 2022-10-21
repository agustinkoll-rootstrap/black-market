package com.rootstrap.android.ui.product_detail

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rootstrap.android.R
import com.rootstrap.android.ui.custom.components.NewLabel
import com.rootstrap.android.ui.custom.components.PrimaryButton
import com.rootstrap.android.ui.custom.components.RestoredLabel
import com.rootstrap.android.ui.dashboard.DashboardBannerShipment
import com.rootstrap.android.ui.ui.theme.BoldBody2
import com.rootstrap.android.ui.ui.theme.MaxToolbarHeight
import com.rootstrap.android.ui.ui.theme.PaddingNormal
import com.rootstrap.domain.Product
import org.koin.androidx.viewmodel.ext.android.getViewModel

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
            imageUrl = imageUrl,
            progress = 1f,
            modifier = Modifier
                .fillMaxWidth()
                .height(MaxToolbarHeight)
        )
    }
}

@Composable
fun CollapsingToolbar(
    imageUrl: String,
    progress: Float,
    modifier: Modifier = Modifier
) {

    Surface(
        color = MaterialTheme.colors.primary,
        elevation = 4.dp,
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

        }
    }
}

@Composable
fun ProductBody(product: Product, addToCartClick: (Product) -> Unit = {}) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
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
    }
}

@Preview
@Composable
fun productDetailPreview() {
    val product =
        Product(
            description = "Description",
            id = 1,
            name = "Chair",
            price = "40",
            imageUrl = "",
            isRestored = false
        )

    ProductBody(product = product)
}