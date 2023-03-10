package com.example.testapprandomusers

import android.app.Application
import com.example.testapprandomusers.modules.clientModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                defaultModule,
                clientModule
            )
        }
    }
}