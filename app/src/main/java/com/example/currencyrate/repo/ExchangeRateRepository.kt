package com.example.currencyrate.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.currencyrate.api.client.RetrofitClient
import com.example.currencyrate.api.model.ExchangeRateResponse
import com.example.currencyrate.model.CurrencyRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExchangeRateRepository {

    // Fetch exchange rates from the API
    suspend fun fetchExchangeRates(): List<CurrencyRate> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.api.getLatestRates().execute()
                if (response.isSuccessful) {
                    val ratesMap = response.body()?.rates ?: emptyMap()
                    ratesMap.map { CurrencyRate(it.key, it.value) }
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }


}