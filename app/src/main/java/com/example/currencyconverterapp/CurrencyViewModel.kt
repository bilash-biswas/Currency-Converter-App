package com.example.currencyconverterapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyViewModel() : ViewModel() {
    private val _exchangeRate = MutableLiveData<Map<String, Double>>()

    val exchangeRate: LiveData<Map<String, Double>> get() = _exchangeRate

    fun fetchExchangeRates(api_key: String, baseCurrency: String) {
        RetrofitInstance.api.getExchangeRates(api_key, baseCurrency).enqueue(object : Callback<ExchangeRateResponse>{
            override fun onResponse(
                call: Call<ExchangeRateResponse?>,
                response: Response<ExchangeRateResponse?>
            ) {
                if (response.isSuccessful){
                    _exchangeRate.value = response.body()?.rates
                }
            }

            override fun onFailure(
                call: Call<ExchangeRateResponse?>,
                t: Throwable
            ) {

            }

        })
    }
}