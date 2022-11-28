package com.rootstrap.android.ui.custom.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.rootstrap.android.ui.ui.theme.PaddingHalf
import com.rootstrap.android.ui.ui.theme.PaddingQuarter
import com.rootstrap.android.ui.ui.theme.RichBlack

@Composable
fun <T> PrimaryButton(
    modifier: Modifier,
    value: T? = null,
    text: String,
    onClick: (T?) -> Unit,
    backgroundColor: Color = RichBlack,
    enabled:Boolean = true,
) {
    Button(
        onClick = { onClick(value) },
        modifier = modifier.clip(RoundedCornerShape(PaddingHalf)),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        enabled = enabled
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(PaddingQuarter)
        )
    }
}
