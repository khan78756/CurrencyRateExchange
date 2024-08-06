package com.example.currencyrate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyrate.R
import com.example.currencyrate.databinding.LayoutCurrencyItemBinding
import com.example.currencyrate.model.CurrencyRate

class CurrencyRateAdapter(private val currencyRates: List<CurrencyRate>) :
    RecyclerView.Adapter<CurrencyRateAdapter.CurrencyRateViewHolder>() {

    inner class CurrencyRateViewHolder(private val binding: LayoutCurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CurrencyRate) {
            binding.apply{
                tvCurrencyCode.text = item.currencyCode
                tvCurrencyRate.text = String.format("%.4f", item.rate)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder {
        val view =
            LayoutCurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyRateViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        holder.bind(currencyRates[position])
    }

    override fun getItemCount() =  currencyRates.size
}