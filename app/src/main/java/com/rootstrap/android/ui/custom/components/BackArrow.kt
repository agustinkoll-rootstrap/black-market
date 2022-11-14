package com.rootstrap.android.ui.custom.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rootstrap.android.ui.ui.theme.PaddingNormal

@Composable
fun BackArrow(modifier: Modifier) {
    IconButton(
        onClick = {},
        modifier = modifier
            .padding(horizontal = PaddingNormal)
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "",
            tint = Color.White
        )
    }
}
