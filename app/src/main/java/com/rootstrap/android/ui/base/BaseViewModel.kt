package com.rootstrap.android.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * A [ViewModel] base class
 * implement app general LiveData as Session or User
 * **/
open class BaseViewModel<UiStateType : UiState>(initialUiState: UiStateType) : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(initialUiState)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    protected val uiState: UiStateType
        get() = _uiStateFlow.value

    protected fun setUiState(function: (state: UiStateType) -> UiStateType) {
        _uiStateFlow.update(function)
    }
}

interface UiState {
    val isVisible: Boolean
}