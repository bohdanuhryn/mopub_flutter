package com.bohdanuhryn.mopub_flutter

import android.app.Activity
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
        activity: Activity,
        messenger: BinaryMessenger,
        id: Int,
        args: HashMap<*, *>?
) : PlatformView, MethodChannel.MethodCallHandler {

    private val channel: MethodChannel = MethodChannel(messenger, "mopub_flutter/banner_$id")

    private val adView: MoPubView = MoPubView(context)

    init {
        channel.setMethodCallHandler(this)
        val size: Map<*, *> = args?.get("adSize") as Map<*, *>
        adView.adUnitId = (args["adUnitId"] as String?) ?: ""
        adView.localExtras = getLocalExtras(activity, size)
        adView.layoutParams = getLayoutParams(context, size)
        adView.bannerAdListener = bannerAdListener()
        adView.loadAd()
    }

    private fun getLocalExtras(activity: Activity, size: Map<*, *>): Map<String, Any> {
        val width = (size["width"] ?: 0) as Int
        val height = (size["height"] ?: 0) as Int
        return mapOf(
                "activity" to activity,
                "adWidth" to width,
                "adHeight" to height
        )
    }

    private fun getLayoutParams(context: Context, size: Map<*, *>): ViewGroup.LayoutParams {
        val displayMetrics = context.resources.displayMetrics
        val width = (size["width"] ?: 0) as Int
        val height = (size["height"] ?: 0) as Int
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
