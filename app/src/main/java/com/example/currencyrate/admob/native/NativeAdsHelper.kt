package com.example.currencyrate.admob.native

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.currencyrate.R
import com.example.currencyrate.utils.isInternetAvailable
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView


class NativeAdsHelper(private val activity: Context) {
    private var nativeAd: NativeAd? = null

    fun setNativeAd(
        shimmerViewContainer: ShimmerFrameLayout? = null,
        frameLayout: FrameLayout,
        layoutId: Int,
        sdIs: String?,
        cardView: CardView,
        onFail: ((String?) -> Unit)? = null,
        onLoad: ((NativeAd?) -> Unit)? = null
    ) {
        cardView.visibility = View.VISIBLE
        showShimmer(shimmerViewContainer)
        if (activity.isInternetAvailable()) {
            val builder = AdLoader.Builder(activity, sdIs!!)
            builder.forNativeAd { unifiedNativeAd: NativeAd ->
                if (nativeAd != null) {
                    nativeAd!!.destroy()
                }
                nativeAd = unifiedNativeAd
                val adView =
                    (activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                        layoutId,
                        null
                    ) as NativeAdView
                populateUnifiedNativeAdView(unifiedNativeAd, adView)
                frameLayout.removeAllViews()
                frameLayout.addView(adView)
                onLoad?.invoke(nativeAd!!)
            }
            val videoOptions = VideoOptions.Builder().setStartMuted(true).build()
            val adOptions = NativeAdOptions.Builder().setVideoOptions(videoOptions).build()
            builder.withNativeAdOptions(adOptions)
            val adLoader = builder
                .withAdListener(object : AdListener() {

                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        Log.e("AddLoad", "onAdLoaded: ")
                        dismissShimmer(shimmerViewContainer)
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
//                    dismissShimmer(shimmerViewContainer)
                        Log.e("AddLoad", "AddFailed: ")
                        Log.e("AddFailed", loadAdError.message + "-ECode " + loadAdError.code)
                        super.onAdFailedToLoad(loadAdError)
//                        nativeAd?.let { onFail?.invoke(loadAdError.message) }
                    }

                })
                .withNativeAdOptions(adOptions)
                .build()
            adLoader.loadAd(AdRequest.Builder().build())
        }
    }

    private fun populateUnifiedNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        val mediaView: MediaView = adView.findViewById(R.id.ad_media)
        adView.mediaView = mediaView
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.bodyView = adView.findViewById(R.id.ad_body)

        adView.starRatingView = adView.findViewById(R.id.ratingbar)

        //Color Change From Remote Config
        val btn: Button = adView.findViewById(R.id.ad_call_to_action)
        btn.backgroundTintList = getCallActionBtnColor("#2d9ea6")
        val adText: TextView = adView.findViewById(R.id.ad_attribution)
        adText.backgroundTintList = getCallActionBtnColor("#2d9ea6")
        val ratingBar : RatingBar = adView.findViewById(R.id.ratingbar)
        ratingBar.progressTintList = getCallActionBtnColor("#2d9ea6")

//        adView.findViewById<AppCompatButton>(R.id.ad_call_to_action)
//            .setBackgroundColor(Color.parseColor(adColor))
        //Color Change From Remote Config


//        adView.mediaView?.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
        if (adView.mediaView != null) {
        } else {
            adView.mediaView?.visibility = View.GONE
        }

        if (nativeAd.headline != null) (adView.headlineView as TextView).text = nativeAd.headline
        if (nativeAd.callToAction == null) {
            adView.callToActionView?.visibility = View.GONE
        } else {
            adView.callToActionView?.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }
        if (nativeAd.icon != null) {
            adView.iconView?.visibility = View.VISIBLE
            (adView.iconView as ImageView).setImageDrawable(nativeAd.icon?.drawable)
        } else adView.iconView?.visibility = View.GONE
        if (nativeAd.starRating == null) {
            adView.starRatingView?.visibility = View.GONE
        }
        if (nativeAd.body == null) {
            adView.bodyView?.visibility = View.GONE
        } else {
            (adView.bodyView as TextView).text = nativeAd.body
            adView.bodyView?.visibility = View.VISIBLE
        }

        if (nativeAd.starRating != null) {
            nativeAd.starRating?.let {
                adView.starRatingView?.visibility = View.VISIBLE
                (adView.starRatingView as RatingBar).rating = it.toFloat()
            } ?: run {
                adView.starRatingView?.visibility = View.GONE
            }
        }

        adView.setNativeAd(nativeAd)
    }

    private fun getCallActionBtnColor(nativeColor: String) =
        ColorStateList.valueOf(Color.parseColor(nativeColor))

    fun showShimmer(shimmerViewContainer: ShimmerFrameLayout?) {
        try {
            shimmerViewContainer?.startShimmer()
            shimmerViewContainer?.visibility = View.VISIBLE
        } catch (e: Exception) {
        }
    }

    fun dismissShimmer(shimmerViewContainer: ShimmerFrameLayout?) {
        try {
            shimmerViewContainer?.visibility = View.GONE
            shimmerViewContainer?.stopShimmer()
        } catch (e: Exception) {
        }
    }

}
