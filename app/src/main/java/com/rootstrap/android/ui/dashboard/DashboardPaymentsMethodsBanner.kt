package com.rootstrap.android.ui.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.rootstrap.android.R
import com.rootstrap.android.ui.ui.theme.PaddingDouble
import com.rootstrap.android.ui.ui.theme.PaddingFiveQuarters
import com.rootstrap.android.ui.ui.theme.PaddingSixQuarters
import com.rootstrap.android.ui.ui.theme.PaddingTenQuarters

@Composable
fun DashboardPaymentsMethodsBanner() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = PaddingSixQuarters)
            .background(MaterialTheme.colors.surface)
    ) {

        Text(
            text = stringResource(R.string.txt_payment_methods),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(top = PaddingDouble)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colors.onSurface
        )

        Row(
            Modifier
                .padding(vertical = PaddingTenQuarters)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            PaymentMethodItem(icon = R.drawable.ic_credit_card, name = stringResource(R.string.txt_credit))
            Divider(Modifier.width(1.dp), color = Color.LightGray)
            PaymentMethodItem(icon = R.drawable.ic_paypal, name = stringResource(R.string.txt_paypal))
            Divider(Modifier.width(1.dp), color = Color.LightGray)
            PaymentMethodItem(icon = R.drawable.ic_crypto, name = stringResource(R.string.txt_crypto))
        }
    }
}

@Composable
fun PaymentMethodItem(@DrawableRes icon: Int, name: String) {
    ConstraintLayout(
        modifier = Modifier.padding(
            horizontal = PaddingSixQuarters
        ),
    ) {
        val (paymentIcon, title) = createRefs()
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(R.string.txt_payment_method_description),
            modifier = Modifier.constrainAs(paymentIcon) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = name,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(paymentIcon.start)
                    end.linkTo(paymentIcon.end)
                    top.linkTo(paymentIcon.bottom, margin = PaddingFiveQuarters)
                }
        )
    }
}

@Preview
@Composable
fun DashboardPaymentsMethodsBannerPreview() {
    DashboardPaymentsMethodsBanner()
}
