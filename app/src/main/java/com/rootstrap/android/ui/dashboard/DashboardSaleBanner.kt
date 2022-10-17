package com.rootstrap.android.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rootstrap.android.R
import com.rootstrap.android.ui.ui.theme.PaddingNormal
import com.rootstrap.android.ui.ui.theme.PaddingQuarter
import com.rootstrap.android.ui.ui.theme.PaddingSixQuarters
import com.rootstrap.android.ui.ui.theme.PublicityBannerHeight
import com.rootstrap.android.ui.ui.theme.PublicityBannerWidth
import com.rootstrap.android.ui.ui.theme.roundedCornersRadiusNormal

@Composable
fun DashboardSaleBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PaddingNormal)
            .padding(top = PaddingSixQuarters)
            .clip(RoundedCornerShape(roundedCornersRadiusNormal))
            .height(PublicityBannerHeight)
            .background(MaterialTheme.colors.primary),
    ) {
        Image(
            painter = painterResource(R.mipmap.publicity_image),
            contentDescription = stringResource(R.string.txt_publicity_image_description),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxHeight()
                .width(PublicityBannerWidth)
        )

        Column(
            modifier = Modifier
                .padding(
                    vertical = PaddingNormal,
                    horizontal = PaddingSixQuarters
                )
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.txt_sale_banner_title),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary
            )

            Text(
                text = stringResource(R.string.txt_sale_banner_description),
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(top = PaddingQuarter)
            )
        }
    }
}

@Preview
@Composable
fun BannerPreview() {
    DashboardSaleBanner()
}
