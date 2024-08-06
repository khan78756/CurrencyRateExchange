package com.example.currencyrate.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.currencyrate.R

class CustomSpinnerAdapter(
    context: Context,
    private val list: List<String>
) : ArrayAdapter<String>(context, R.layout.spinner_item_layout, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.spinner_item_layout, parent, false)
        Log.e("languagesSpinner**", "viewModelObserver: HomeFragment 5")

        val languageTextView: TextView = view.findViewById(R.id.languageTextView)

        val language = list[position]
        languageTextView.text = language

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = getView(position, convertView, parent)
//        val height = 60 // Set your desired static height here
//        view.layoutParams = AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        return view
    }
}
