package com.example.currencyrate.admob.appopen

import android.app.Activity
import com.example.currencyrate.R
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.example.currencyrate.admob.loadingscreen.AppOpenLoading
import kotlin.math.truncate

class SplashOpenApp(
    val activity: Activity,
) {

    private var AD_UNIT_ID = activity.getString(R.string.splash_open_app)
    private var isShowingAd = true
    private var splashAppOpen: AppOpenAd? = null
    private lateinit var loadCallback: AppOpenAd.AppOpenAdLoadCallback
    private var appOpenLoading: AppOpenLoading? = null

    fun showAdIfAvailable(onAdLoaded: (() -> Unit)? = null) {
        if (isShowingAd && isAdAvailable()) {

            val fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    appOpenLoading?.dismiss()
                    onAdLoaded?.invoke()
                    isShowingAd = true
                    super.onAdFailedToShowFullScreenContent(p0)
                }

                override fun onAdShowedFullScreenContent() {
                    isShowingAd = false
                }

                override fun onAdDismissedFullScreenContent() {
                    splashAppOpen = null
                    isShowingAd = true
                    appOpenLoading?.dismiss()
                    onAdLoaded?.invoke()


                }
            }
            splashAppOpen?.fullScreenContentCallback = fullScreenContentCallback
            activity.let {
                showLoading()
                if (isShowingAd) {
                    isShowingAd = false
                    splashAppOpen?.show(it)
                }
            }
        } else {
            onAdLoaded?.invoke()
        }
    }

    private fun showLoading() {
        activity.let {
            if (!it.isFinishing) {
                appOpenLoading = AppOpenLoading(it)
                appOpenLoading?.show()
            }
        }
    }

    fun fetchAd(onAction : (Boolean) -> Unit) {

        if (isAdAvailable()) {
            return
        }
        loadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(p0: AppOpenAd) {
                splashAppOpen = p0
                appOpenLoading?.dismiss()
                showAdIfAvailable {
                    onAction.invoke(true)
                }

            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                // onAdLoaded?.invoke()
                appOpenLoading?.dismiss()
                super.onAdFailedToLoad(p0)
                onAction.invoke(false)
            }
        }

        val request = getAdRequest()
        AppOpenAd.load(activity, AD_UNIT_ID, request,AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback)
    }

    private fun getAdRequest(): AdRequest {
        return AdRequest.Builder().build()
    }

    private fun isAdAvailable(): Boolean {
        return splashAppOpen != null
    }
}