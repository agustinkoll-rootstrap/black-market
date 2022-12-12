package com.rootstrap.android.ui.login

import androidx.lifecycle.viewModelScope
import com.rootstrap.android.ui.base.BaseViewModel
import com.rootstrap.android.ui.base.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel :
    BaseViewModel<LoginUiState>(LoginUiState(email = "", password = "", isLoginEnabled = false)) {

    private val _navigationEventFlow = MutableSharedFlow<LoginNavigationEvent>()
    val navigationEventFlow = _navigationEventFlow.asSharedFlow()

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
            uiState.copy(
                password = password,
                isLoginEnabled = isLoginEnabled()
            )
        }
    }

    fun onLoginClick() {
        setUiState {
            uiState.copy(isLoading = true, isLoginEnabled = isLoginEnabled(true))
        }
        viewModelScope.launch {
            delay(3000)
           //
            setUiState {
                uiState.copy(isLoading = false, animateSuccess = true)
            }
        }
    }

    fun onLoginSuccessAnimationFinished(){
        viewModelScope.launch {
            _navigationEventFlow.emit(LoginNavigationEvent.Dashboard)
        }
    }

    private fun isLoginEnabled(isLoading: Boolean = false) =
        uiState.email.isNotEmpty() && uiState.password.isNotEmpty() && isLoading.not()
}

data class LoginUiState(
    override val isVisible: Boolean = true,
    val email: String,
    val password: String,
    val isLoginEnabled: Boolean,
    val isLoading: Boolean = false,
    val animateSuccess:Boolean = false,
) : UiState

sealed class LoginNavigationEvent {
    object Dashboard : LoginNavigationEvent()
    object None : LoginNavigationEvent()
}