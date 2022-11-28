package com.rootstrap.android.ui.login

import com.rootstrap.android.ui.base.BaseViewModel
import com.rootstrap.android.ui.base.UiState

class LoginViewModel : BaseViewModel<LoginUiState>(LoginUiState(email = "", password = "", isLoginEnabled = false)) {

    fun onEmailChanged(email: String) {
        setUiState {
            uiState.copy(
                email = email,
                isLoginEnabled = isLoginEnabled()
            )
        }
    }

    fun onPasswordChanged(password: String) {
        setUiState {
            uiState.copy(password = password, isLoginEnabled  = isLoginEnabled())
        }
    }

    private fun isLoginEnabled() =
       uiState.email.isNotEmpty() && uiState.password.isNotEmpty()
}

data class LoginUiState(
    override val isVisible: Boolean = true,
    val email: String,
    val password: String,
    val isLoginEnabled: Boolean,
) : UiState