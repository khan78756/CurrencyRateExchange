package com.example.currencyrate.api.client

import com.example.currencyrate.api.ExchangeRateApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://open.er-api.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ExchangeRateApi by lazy {
        retrofit.create(ExchangeRateApi::class.java)
    }
}