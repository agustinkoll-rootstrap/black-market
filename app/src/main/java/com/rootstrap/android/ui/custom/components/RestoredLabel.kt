package com.rootstrap.android.ui.custom.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rootstrap.android.R
import com.rootstrap.android.ui.ui.theme.RestoredGreen

@Composable
fun RestoredLabel() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10))
            .background(RestoredGreen)

    ) {
        Text(
            text = stringResource(R.string.txt_restored),
            modifier = Modifier.padding(2.dp),
            color = Color.White
        )
    }
}
