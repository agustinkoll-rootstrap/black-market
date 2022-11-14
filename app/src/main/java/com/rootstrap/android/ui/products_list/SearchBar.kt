package com.rootstrap.android.ui.products_list

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rootstrap.android.R

@Composable
fun SearchBar(
    value: String,
    modifier: Modifier,
    onValueChanged: (String) -> Unit,
    onClearTextClick: () -> Unit,
) {
    val hasToShowCleanIcon = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { query ->
            hasToShowCleanIcon.value = query.isNotEmpty()
            onValueChanged(query)
        },
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        trailingIcon = {
            if (hasToShowCleanIcon.value) {
                IconButton(onClick = {
                    hasToShowCleanIcon.value = false
                    onClearTextClick()
                }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(R.string.clear))
                }
            } else {
                Icon(imageVector = Icons.Filled.Search, contentDescription = stringResource(R.string.search))
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        shape = RoundedCornerShape(8.dp),
    )
}
