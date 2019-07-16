package com.bohdanuhryn.mopub_flutter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.mopub.mobileads.MoPubView
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.applyDimension
import com.mopub.mobileads.MoPubErrorCode

class MopubBanner(
        context: Context,
        messenger: BinaryMessenger,
        id: Int,
        args: HashMap<*, *>?
) : PlatformView, MethodChannel.MethodCallHandler {

    private val channel: MethodChannel = MethodChannel(messenger, "mopub_flutter/banner_$id")

    private val adView: MoPubView = MoPubView(context)

    init {
        channel.setMethodCallHandler(this)
        adView.adUnitId = args?.get("adUnitId") as String?
        adView.layoutParams = layoutParams(context, args ?: HashMap<Any, Any>())
        adView.bannerAdListener = bannerAdListener()
        adView.loadAd()
    }

    private fun layoutParams(context: Context, args: HashMap<*, *>): ViewGroup.LayoutParams {
        val sizeMap = args["adSize"] as Map<*, *>
        val displayMetrics = context.resources.displayMetrics
        val width = (sizeMap["width"] ?: 0) as Int
        val height = (sizeMap["height"] ?: 0) as Int
        val widthPx = applyDimension(COMPLEX_UNIT_DIP, width.toFloat(), displayMetrics)
        val heightPx = applyDimension(COMPLEX_UNIT_DIP, height.toFloat(), displayMetrics)
        return ViewGroup.LayoutParams(widthPx.toInt(), heightPx.toInt())
    }

    private fun bannerAdListener(): MoPubView.BannerAdListener {
        return object : MoPubView.BannerAdListener {

            override fun onBannerExpanded(banner: MoPubView?) {
                channel.invokeMethod("expanded", null)
            }

            override fun onBannerLoaded(banner: MoPubView?) {
                channel.invokeMethod("loaded", null)
            }

            override fun onBannerCollapsed(banner: MoPubView?) {
                channel.invokeMethod("collapsed", null)
            }

            override fun onBannerFailed(banner: MoPubView?, errorCode: MoPubErrorCode?) {
                channel.invokeMethod("failed", hashMapOf(
                        "errorCode" to errorCode?.intCode,
                        "errorName" to errorCode?.name
                ))
            }

            override fun onBannerClicked(banner: MoPubView?) {
                channel.invokeMethod("clicked", null)
            }

        }
    }

    override fun getView(): View {
        return adView
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when(call.method) {
            "dispose" -> dispose()
            else -> result.notImplemented()
        }
    }

    override fun dispose() {
        adView.visibility = View.GONE
        adView.destroy()
        channel.setMethodCallHandler(null)
    }

}
