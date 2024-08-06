package com.example.currencyrate.businesslogic

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.currencyrate.api.client.RetrofitClient
import com.example.currencyrate.api.model.ExchangeRateResponse
import com.example.currencyrate.repo.ExchangeRateRepository
import retrofit2.Response

class ExchangeRateWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val repository = ExchangeRateRepository()
        return try {
            repository.fetchExchangeRates()
            Log.d("Fetch***", "Fetched  rates at ${System.currentTimeMillis()}")
            Result.success()
        } catch (e: Exception) {
            Log.e("Fetch***", "Error fetching rates", e)
            Result.failure()
        }
    }
}