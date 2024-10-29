package com.example.currencyconverterapp

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("rates") val rates: Map<String, Double>,
    @SerializedName("base") val base: String,
)
