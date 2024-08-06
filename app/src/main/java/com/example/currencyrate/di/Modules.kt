package com.example.currencyrate.di

import com.example.currencyrate.repo.ExchangeRateRepository
import com.example.currencyrate.viewmodel.ExchangeRateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val diModule = module {
    single { ExchangeRateRepository() }
    viewModel { ExchangeRateViewModel(get()) }
}