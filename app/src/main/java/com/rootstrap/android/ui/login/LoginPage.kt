package com.rootstrap.android.ui.login

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rootstrap.android.R
import com.rootstrap.android.ui.custom.components.PrimaryButton
import com.rootstrap.android.ui.ui.theme.PaddingNormal
import org.koin.androidx.viewmodel.ext.android.getViewModel

@Composable
fun LoginPage() {
    val context = LocalContext.current as ComponentActivity
    val loginViewModel: LoginViewModel =
        context.getViewModel<LoginViewModel>()
    val uiState: LoginUiState by loginViewModel.uiStateFlow.collectAsState()
    LoginPage(uiState,
        { query -> loginViewModel.onEmailChanged(query) },
        { query -> loginViewModel.onPasswordChanged(query) })
}

@Composable
fun LoginPage(
    uiState: LoginUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
) {

    Column(modifier = Modifier.fillMaxSize().background(Color.LightGray)) {
        LoginBody(
            uiState,
            onEmailChanged,
            onPasswordChanged
        )
    }
}

@Composable
fun LoginBody(
    uiState: LoginUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
) {
    val isPasswordVisible = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .padding(horizontal = PaddingNormal)
            .clip(shape = RoundedCornerShape(4))
            .background(Color.White)
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = PaddingNormal),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(R.drawable.ic_logo),
                contentDescription = stringResource(R.string.app_logo_content_logo)
            )
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = stringResource(R.string.txt_title),
                style = MaterialTheme.typography.subtitle1
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = PaddingNormal)
                .padding(horizontal = PaddingNormal),
            value = uiState.email,
            onValueChange = { onEmailChanged(it) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            label = { Text(text = "Email") },
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = PaddingNormal)
                .padding(horizontal = PaddingNormal),
            value = uiState.password,
            onValueChange = { query -> onPasswordChanged(query) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            label = { Text(text = "password") },
            trailingIcon = {
                IconToggleButton(checked = isPasswordVisible.value, onCheckedChange = {
                    isPasswordVisible.value = isPasswordVisible.value.not()
                }) {
                    Icon(
                        imageVector =
                        if (isPasswordVisible.value)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff,
                        contentDescription = ""
                    )
                }
            },
            visualTransformation = if (isPasswordVisible.value)
                VisualTransformation.None
            else PasswordVisualTransformation()
        )

        PrimaryButton<String>(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .padding(vertical = PaddingNormal)
                .padding(horizontal = PaddingNormal),
            enabled = uiState.isLoginEnabled,
            text = "Log in"
        )
    }
}

@Preview
@Composable
fun LoginPagePreview() {
    LoginPage( LoginUiState(isLoginEnabled = true, email = "", password = ""),{},{})
}