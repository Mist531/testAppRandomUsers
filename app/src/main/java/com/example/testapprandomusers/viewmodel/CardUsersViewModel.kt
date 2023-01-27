package com.example.testapprandomusers.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.optics.optics
import com.example.testapprandomusers.models.InfoModel
import com.example.testapprandomusers.models.ResultModel
import com.example.testapprandomusers.repositories.UsersRepositories
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.uuid.UUID
import org.koin.android.annotation.KoinViewModel

@optics
@Immutable
data class CardUsersState(
    val usersInfo: List<ResultModel> = emptyList(),
    val selectUserId: UUID? = null,
    val pageInfo: InfoModel? = null,
) {
    companion object
}

sealed interface CardUsersScreenEvent {
    data class SelectUserId(val id: UUID) : CardUsersScreenEvent
    object GetInfo : CardUsersScreenEvent
}

@KoinViewModel
class CardUsersViewModel(
    usersRepositories: UsersRepositories
) : ViewModel() {

    private val signals = Channel<CardUsersScreenEvent>()

    fun pushSignal(event: CardUsersScreenEvent) {
        viewModelScope.launch {
            signals.send(event)
        }
    }

    init {
        viewModelScope.launch {
            for (event in signals) {
                when (event) {
                    is CardUsersScreenEvent.SelectUserId -> {
                        _state.update {
                            CardUsersState.selectUserId.modify(it) {
                                event.id
                            }
                        }
                    }

                    is CardUsersScreenEvent.GetInfo -> {
                        usersRepositories.pushSignal(
                            UsersRepositories.UsersRepositoriesSignals.FetchInformation
                        )
                    }
                }
            }
        }
    }

    private val _state = MutableStateFlow(CardUsersState())
    val state: StateFlow<CardUsersState> =
        _state.combine(
            usersRepositories.usersInfoState
        ) { state, repositoryState ->
            CardUsersState.usersInfo.modify(state) {
                repositoryState.results
            }
            CardUsersState.pageInfo.modify(state) {
                repositoryState.info
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CardUsersState()
        )
}