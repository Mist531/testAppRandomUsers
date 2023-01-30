package com.example.testapprandomusers.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.optics.optics
import com.example.testapprandomusers.data.exception.AppException
import com.example.testapprandomusers.data.stores.AppExceptionStore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@optics
@Immutable
data class SettingsState(
    val exception: List<AppException> = emptyList(),
) {
    companion object
}

sealed interface SettingsScreenEvent {

    object ClearException : SettingsScreenEvent
}

@KoinViewModel
class SettingsViewModel(
    appExceptionStore: AppExceptionStore
) : ViewModel() {

    private val signals = Channel<SettingsScreenEvent>()

    fun pushSignal(event: SettingsScreenEvent) {
        viewModelScope.launch {
            signals.send(event)
        }
    }

    init {
        viewModelScope.launch {
            for (event in signals) {
                when (event) {
                    is SettingsScreenEvent.ClearException -> {
                        appExceptionStore.pushSignal(AppExceptionStore.AppExceptionStoreSignals.ClearAppException)
                    }
                }
            }
        }
    }

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> =
        _state.combine(
            appExceptionStore.exception
        ) { state, repositoryState ->
            SettingsState(
                exception = repositoryState.listException
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SettingsState()
        )
}