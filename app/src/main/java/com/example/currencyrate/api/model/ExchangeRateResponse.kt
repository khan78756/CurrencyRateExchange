package com.example.currencyrate.api.model

import com.example.currencyrate.model.CurrencyRate
import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("base_code")
    val baseCode: String,

    @SerializedName("rates")
    val rates: Map<String, Double>
)