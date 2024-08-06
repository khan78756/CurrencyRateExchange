package com.example.currencyrate.businesslogic

import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit


object ExchangeRateScheduler {
    fun scheduleRateUpdates() {
        val rateUpdateRequest = PeriodicWorkRequestBuilder<ExchangeRateWorker>(20, TimeUnit.MINUTES).build()
        WorkManager.getInstance().enqueue(rateUpdateRequest)
    }
}