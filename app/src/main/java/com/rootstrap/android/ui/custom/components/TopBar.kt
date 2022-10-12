package com.rootstrap.android.ui.custom.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rootstrap.android.R

@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Icon(
                    painterResource(R.drawable.ic_logo), contentDescription = stringResource(R.string.app_logo_content_logo)
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = stringResource(R.string.txt_title),
                    style = MaterialTheme.typography.subtitle1
                )
            }
        },
    )
}
