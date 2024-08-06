package com.example.currencyrate.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyrate.R
import com.example.currencyrate.adapter.CurrencyRateAdapter
import com.example.currencyrate.admob.native.NativeAdsHelper
import com.example.currencyrate.databinding.ActivityMainBinding
import com.example.currencyrate.utils.setupSpinner
import com.example.currencyrate.viewmodel.ExchangeRateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val exchangeRateViewModel: ExchangeRateViewModel by viewModel()
    private lateinit var currencyRateAdapter: CurrencyRateAdapter
    private var currencies = emptyList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchData()
        binding.observer()
        binding.convertCurrency()

    }


    private fun ActivityMainBinding.setupData() {
        rvCurrency.layoutManager = LinearLayoutManager(this@MainActivity)
        rvCurrency.adapter = currencyRateAdapter
    }

    private fun fetchData() {
        exchangeRateViewModel.currencyRates.observe(this) { rates ->
            currencies = rates.map { it.currencyCode }
            currencyRateAdapter = CurrencyRateAdapter(rates)
            binding.setupData()
            binding.setupSpinners()

        }
    }

    private fun ActivityMainBinding.setupSpinners() {
        spinnerSource.setupSpinner(currencies,{
            val sourceCurrency = currencies[it]
            exchangeRateViewModel.setSourceCurrency(sourceCurrency)
            binding.resultTextview.text = "0.0"
            binding.sourceEt.text.clear()
            binding.sourceEt.requestFocus()
        },1)
        spinnerTarget.setupSpinner(currencies,{
            val targetCurrency = currencies[it]
            exchangeRateViewModel.setTargetCurrency(targetCurrency)
            binding.resultTextview.text = "0.0"
            binding.sourceEt.text.clear()
            binding.sourceEt.requestFocus()
        },1)

    }

    private fun ActivityMainBinding.convertCurrency() {
        sourceEt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                if(s.isNotEmpty()){
                    exchangeRateViewModel.convertCurrency(s.toString().toDouble())
                }else{
                    binding.resultTextview.text = ""
                }

            }
        })
    }

    private fun loadSmallNativeAd() {
        NativeAdsHelper(this).setNativeAd(
            binding.adLayout.shimmerContainerSmall,
            binding.adLayout.frameLayoutSmall,
            R.layout.small_native_ad,
            "ca-app-pub-3940256099942544/2247696110",
            binding.adLayout.cardView
        )
    }

    private fun ActivityMainBinding.observer(){
        exchangeRateViewModel.conversionResult.observe(this@MainActivity, Observer { result ->
            resultTextview.text = String.format(Locale.US,"%.2f", result)
        })
    }

    override fun onPostResume() {
        super.onPostResume()
        loadSmallNativeAd()
    }
}