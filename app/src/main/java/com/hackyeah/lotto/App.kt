package com.hackyeah.lotto

import android.app.Application
import com.hackyeah.lotto.common.di.commonModule
import com.hackyeah.lotto.registration.di.registrationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Android context
            androidContext(this@App)
            // modules
            modules(
                listOf(
                    commonModule,
                    registrationModule
                )
            )
        }
    }
}