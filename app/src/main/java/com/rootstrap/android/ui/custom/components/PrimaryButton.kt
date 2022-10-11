package com.rootstrap.android.ui.custom.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun <T> PrimaryButton(
    modifier: Modifier,
    value: T? = null,
    text: String,
    onClick: (T?) -> Unit,
) {
    Button(
        onClick = { onClick(value) },
        modifier = modifier.clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(4.dp)
        )
    }
}