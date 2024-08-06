package com.example.currencyrate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyrate.model.CurrencyRate
import com.example.currencyrate.repo.ExchangeRateRepository
import kotlinx.coroutines.launch

class ExchangeRateViewModel(private val repository: ExchangeRateRepository) : ViewModel() {


    private val _sourceCurrency = MutableLiveData<String>()
    val sourceCurrency: LiveData<String> get() = _sourceCurrency

    private val _targetCurrency = MutableLiveData<String>()
    val targetCurrency: LiveData<String> get() = _targetCurrency

    private val _conversionResult = MutableLiveData<Double>()
    val conversionResult: LiveData<Double> get() = _conversionResult

    private val _currencyRates = MutableLiveData<List<CurrencyRate>>()
    val currencyRates: LiveData<List<CurrencyRate>> get() = _currencyRates

   init {
       viewModelScope.launch() {
           val rates = repository.fetchExchangeRates()
           _currencyRates.postValue(rates)
       }
   }

    fun setSourceCurrency(currency: String) {
        _sourceCurrency.value = currency
    }

    fun setTargetCurrency(currency: String) {
        _targetCurrency.value = currency
    }

    fun convertCurrency(amount: Double) {
        val rates = _currencyRates.value
        if (rates != null && _sourceCurrency.value != null && _targetCurrency.value != null) {
            val sourceRate = rates.find { it.currencyCode == _sourceCurrency.value }?.rate ?: return
            val targetRate = rates.find { it.currencyCode == _targetCurrency.value }?.rate ?: return
            val result = amount * targetRate / sourceRate

            _conversionResult.postValue(result)
        }
    }
}