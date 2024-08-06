package com.example.currencyrate.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.example.currencyrate.adapter.CustomSpinnerAdapter

fun Spinner.setupSpinner(list: List<String>, onItemSelected: (Int) -> Unit, selection: Int = -1) {

    val spinnerAdapter = CustomSpinnerAdapter(
        context, list
    )
    adapter = spinnerAdapter
    if (selection != -1)
        setSelection(selection)

    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            onItemSelected.invoke(p2)

        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }
}

fun Context.isInternetAvailable(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var activeNetworkInfo: NetworkInfo? = null
    activeNetworkInfo = cm.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
}
