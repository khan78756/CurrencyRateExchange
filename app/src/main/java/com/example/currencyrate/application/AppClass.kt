package com.example.currencyrate.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.currencyrate.businesslogic.ExchangeRateScheduler
import com.example.currencyrate.di.InitKoin

class AppClass(): Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        ExchangeRateScheduler.scheduleRateUpdates()
        InitKoin(this).load()
    }
}