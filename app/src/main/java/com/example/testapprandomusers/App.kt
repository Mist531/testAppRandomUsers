package com.example.testapprandomusers

import android.app.Application
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                //defaultModule,
                module {
                    single {
                        Json {
                            isLenient = true
                            prettyPrint = true
                        }
                    }
                }
            )
        }
    }
}