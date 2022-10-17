package com.rootstrap.android.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.rootstrap.android.R
import com.rootstrap.android.ui.ui.theme.*

@Composable
fun DashboardBannerShipment() {
    val annotatedShipmentDescription = buildAnnotatedString {
        append(stringResource(id = R.string.txt_shipment_powered_by))
        append(" ")
        withStyle(style = SpanStyle(Color.Green)) {
            append(stringResource(id = R.string.txt_shipment_fedex))
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(ShipmentHeight)
            .padding(horizontal = PaddingNormal)
            .padding(vertical = PaddingSixQuarters)
            .clip(RoundedCornerShape(RoundedCornersRadiusNormal))
            .background(MaterialTheme.colors.primary),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = PaddingNormal,
                )
                .padding(start = PaddingSixQuarters, end = PaddingHalf)
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.txt_shipment_title),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary
            )

            Text(
                text = annotatedShipmentDescription,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(top = PaddingQuarter)
            )
        }

        Image(
            painter = painterResource(R.mipmap.shipment),
            contentDescription = stringResource(R.string.txt_publicity_image_description),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxHeight()
                .width(PublicityBannerWidth)
        )
    }
}

@Preview
@Composable
fun DashboardSaleBannerPreview() {
    DashboardBannerShipment()
}
