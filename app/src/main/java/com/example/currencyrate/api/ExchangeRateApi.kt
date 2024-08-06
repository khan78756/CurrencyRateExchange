package com.example.currencyrate.api

import com.example.currencyrate.api.model.ExchangeRateResponse
import retrofit2.Call
import retrofit2.http.GET

interface ExchangeRateApi {
    @GET("v6/latest/USD")
    fun getLatestRates(): Call<ExchangeRateResponse>
}