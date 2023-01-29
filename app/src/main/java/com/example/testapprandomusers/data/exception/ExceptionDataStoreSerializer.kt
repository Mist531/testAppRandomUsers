package com.example.testapprandomusers.data.exception

import android.content.Context
import androidx.datastore.dataStore
import com.example.testapprandomusers.models.exeptions.ExceptionDataStoreModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import java.io.InputStream
import java.io.OutputStream
import androidx.datastore.core.Serializer

val Context.appExceptionDataStore by dataStore("exception.json", ExceptionDataStoreSerializer)

object ExceptionDataStoreSerializer : Serializer<ExceptionDataStoreModel>, KoinComponent {
    override val defaultValue: ExceptionDataStoreModel
        get() = ExceptionDataStoreModel()

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun readFrom(input: InputStream): ExceptionDataStoreModel {
        return runCatching {
            get<Json>().decodeFromStream<ExceptionDataStoreModel>(input)
        }.getOrDefault(defaultValue)
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun writeTo(t: ExceptionDataStoreModel, output: OutputStream) {
        get<Json>().encodeToStream(t, output)
    }

}
