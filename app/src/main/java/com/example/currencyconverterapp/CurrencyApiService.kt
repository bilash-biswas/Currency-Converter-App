package com.example.currencyconverterapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("latest")
    fun getExchangeRates(
        @Query("access_key") apiKey : String,
        @Query("base") baseCurrency : String,
    ) : Call<ExchangeRateResponse>
}