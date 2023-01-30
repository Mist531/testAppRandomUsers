package com.example.testapprandomusers.data.stores

import android.content.Context
import com.example.testapprandomusers.data.exception.AppException
import com.example.testapprandomusers.data.datastore_serializers.appExceptionDataStore
import com.example.testapprandomusers.models.exeptions.ExceptionDataStoreModel
import com.example.testapprandomusers.models.exeptions.listException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

interface AppExceptionStore {
    val exception: Flow<ExceptionDataStoreModel>

    sealed interface AppExceptionStoreSignals {
        class AddAppException( val exception: AppException) : AppExceptionStoreSignals

        object ClearAppException : AppExceptionStoreSignals
    }

    fun pushSignal(signal: AppExceptionStoreSignals)
}

@Single(binds = [AppExceptionStore::class])
class AppExceptionStoreImpl(
    context: Context
) : AppExceptionStore {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val datastore = context.appExceptionDataStore

    override val exception: Flow<ExceptionDataStoreModel> = datastore.data

    override fun pushSignal(signal: AppExceptionStore.AppExceptionStoreSignals) {
        when (signal) {
            is AppExceptionStore.AppExceptionStoreSignals.AddAppException -> addAppException(
                signal.exception
            )
            is AppExceptionStore.AppExceptionStoreSignals.ClearAppException -> clearAppException()
        }
    }

    private fun addAppException(exception: AppException) {
        scope.launch {
            datastore.updateData { storeModel ->
                ExceptionDataStoreModel.listException.modify(storeModel) {
                    it + exception
                }
            }
        }
    }

    private fun clearAppException() {
        scope.launch {
            datastore.updateData { storeModel ->
                ExceptionDataStoreModel.listException.modify(storeModel) {
                    emptyList()
                }
            }
        }
    }
}