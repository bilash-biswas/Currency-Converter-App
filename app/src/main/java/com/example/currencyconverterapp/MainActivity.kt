package com.example.currencyconverterapp

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.currencyconverterapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CurrencyViewModel by viewModels()
    private val apiKey = "d917ead4e16c31d812bfe96dd1c4426d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.fetchExchangeRates(apiKey, "USD")

        // Observing the exchange rate data
        viewModel.exchangeRate.observe(this, Observer { rates ->
            rates?.let {
                Log.d("@@@", "Exchange rates: $it")
                val currencies = it.keys.toList()
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                binding.toSpinner.adapter = adapter
                binding.fromSpinner.adapter = adapter
            } ?: run {
                Toast.makeText(this, "Failed to load exchange rates", Toast.LENGTH_SHORT).show()
            }
        })



        // Setting up the Convert button functionality
        binding.submit.setOnClickListener {
            val amountString = binding.amount.text.toString()
            val amount = amountString.toDoubleOrNull()
            val selectedFromCurrency = binding.fromSpinner.selectedItem?.toString()
            val selectedToCurrency = binding.toSpinner.selectedItem?.toString()

            if (amount != null && selectedToCurrency != null && selectedFromCurrency != null) {
                val rates = viewModel.exchangeRate.value
                val fromRate = rates?.get(selectedFromCurrency)
                val toRate = rates?.get(selectedToCurrency)
                if (fromRate != null && toRate != null) {
                    val convertedAmount = (amount / fromRate) * toRate
                    binding.result.text = "$convertedAmount $selectedToCurrency"
                } else {
                    Toast.makeText(this, "Conversion rate not available", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a valid amount and select a currency", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
