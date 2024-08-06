package com.example.currencyrate.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.startKoin


class InitKoin(var context : Application) {

    fun load() {
        startKoin {
            androidContext(context)
            androidLogger()
            loadKoinModules(listOf(diModule))
        }
    }

}