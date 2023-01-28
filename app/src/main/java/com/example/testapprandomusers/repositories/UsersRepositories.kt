package com.example.testapprandomusers.repositories

import arrow.core.Either
import com.example.testapprandomusers.models.UsersInfoModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

sealed interface UsersRepositories {

    val usersInfoState: Flow<UsersInfoModel>

    sealed interface UsersRepositoriesSignals {
        object FetchInformation : UsersRepositoriesSignals
    }

    fun pushSignal(signal: UsersRepositoriesSignals)
}

@Single(binds = [UsersRepositories::class])
class UsersRepositoriesImpl(
    private val client: HttpClient
) : UsersRepositories {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    //Signals Flow
    private val _signals = Channel<UsersRepositories.UsersRepositoriesSignals>()
    private val signals = _signals.consumeAsFlow()
    override fun pushSignal(signal: UsersRepositories.UsersRepositoriesSignals) {
        scope.launch {
            _signals.send(signal)
        }
    }

    //State Flow
    private val _usersInfoState = Channel<UsersInfoModel>()
    override val usersInfoState: Flow<UsersInfoModel> =
        _usersInfoState.receiveAsFlow().distinctUntilChanged()

    private suspend fun fetchInformation() =
        Either.catch(::exceptionHandling) {
            client.get("https://randomuser.me/api/?results=10").body<UsersInfoModel>()
        }.fold(
            ifLeft = {
            },
            ifRight = {
                _usersInfoState.send(it)
            }
        )

    init {
        scope.launch {
            signals.collectLatest {
                when (it) {
                    UsersRepositories.UsersRepositoriesSignals.FetchInformation -> {
                        fetchInformation()
                    }
                }
            }
        }
    }

}