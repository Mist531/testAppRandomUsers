package com.example.testapprandomusers.viewmodel

import android.util.Log
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
    val isRefreshing: Boolean = false
) {
    companion object
}

sealed interface CardUsersScreenEvent {
    data class SelectUserId(
        val id: UUID
    ) : CardUsersScreenEvent

    data class GetInfo(
        val page: Int
    ) : CardUsersScreenEvent

    object NextPage : CardUsersScreenEvent

    object PrevPage : CardUsersScreenEvent
}

@KoinViewModel
class CardUsersViewModel(
    usersRepositories: UsersRepositories
) : ViewModel() {

    private val maxPage = 10
    private val countPerson = 10

    private val signals = Channel<CardUsersScreenEvent>()

    fun pushSignal(event: CardUsersScreenEvent) {
        viewModelScope.launch {
            signals.send(event)
        }
    }

    init {
        viewModelScope.launch {
            pushSignal(CardUsersScreenEvent.GetInfo(page = 1))
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
                            UsersRepositories.UsersRepositoriesSignals.FetchInformation(
                                page = event.page,
                                countPerson = countPerson
                            )
                        )
                    }
                    is CardUsersScreenEvent.NextPage -> {
                        _state.value.pageInfo?.let {
                            if (it.page < maxPage) {
                                pushSignal(
                                    CardUsersScreenEvent.GetInfo(
                                        page = it.page + 1
                                    )
                                )
                            }
                        }
                    }
                    is CardUsersScreenEvent.PrevPage -> {
                        _state.value.pageInfo?.let {
                            if (it.page > 1) {
                                pushSignal(
                                    CardUsersScreenEvent.GetInfo(
                                        page = it.page - 1
                                    )
                                )
                            }
                        }
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
            Log.e("TAGREPSTATE", "repositoryState: $repositoryState")
            CardUsersState(
                usersInfo = repositoryState.results,
                pageInfo = repositoryState.info,
                selectUserId = state.selectUserId
            )
            /*CardUsersState.let {
                it.usersInfo.modify(state) {
                    repositoryState.results
                }
                it.pageInfo.modify(state) {
                    repositoryState.info
                }
                it.selectUserId.modify(state) {
                    state.selectUserId
                }
                //this.selectUserId.set(state, state.selectUserId)
            }*/
            /*CardUsersState.usersInfo.modify(state) {
                repositoryState.results
            }*/
            /*repositoryState.info.let {
                CardUsersState.pageInfo.modify(state) {
                    it
                }
            }*/
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CardUsersState()
        )
}