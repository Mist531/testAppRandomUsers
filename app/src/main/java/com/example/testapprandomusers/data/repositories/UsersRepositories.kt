package com.example.testapprandomusers.data.repositories

import arrow.core.Either
import com.example.testapprandomusers.data.exception.exceptionHandling
import com.example.testapprandomusers.data.stores.AppExceptionStore
import com.example.testapprandomusers.models.users_repositories.UsersInfoModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

sealed interface UsersRepositories {

    val usersInfoState: Flow<UsersInfoModel>

    sealed interface UsersRepositoriesSignals {
        data class FetchInformation(
            val page: Int,
            val countPerson: Int
        ) : UsersRepositoriesSignals
    }

    fun pushSignal(signal: UsersRepositoriesSignals)
}

@Single(binds = [UsersRepositories::class])
class UsersRepositoriesImpl(
    private val client: HttpClient,
    private val appExceptionStore: AppExceptionStore
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

    private suspend fun fetchInformation(
        countPerson: Int,
        page: Int
    ) =
        Either.catch(::exceptionHandling) {
            client.get("https://randomuser.me/api/?results=$countPerson&page=$page&seed=abc")
                .body<UsersInfoModel>()
        }.fold(
            ifLeft = {
                appExceptionStore.pushSignal(
                    AppExceptionStore.AppExceptionStoreSignals.AddAppException(it)
                )
            },
            ifRight = {
                _usersInfoState.send(it)
            }
        )

    init {
        scope.launch {
            for (event in _signals) {
                when (event) {
                    is UsersRepositories.UsersRepositoriesSignals.FetchInformation -> {
                        fetchInformation(
                            countPerson = event.countPerson,
                            page = event.page
                        )
                    }
                }
            }
        }
    }
}