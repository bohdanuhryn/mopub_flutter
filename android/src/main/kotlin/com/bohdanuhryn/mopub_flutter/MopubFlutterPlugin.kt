package com.bohdanuhryn.mopub_flutter

import android.content.Context
import android.util.Log
import com.mopub.common.MediationSettings
import com.mopub.common.MoPub
import com.mopub.common.SdkConfiguration
import com.mopub.common.logging.MoPubLog
import com.mopub.mobileads.MoPubView
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

class MopubFlutterPlugin(private val context: Context): MethodCallHandler {

  private val TAG: String = MopubFlutterPlugin::class.java.simpleName

  companion object {

    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val defaultChannel = MethodChannel(registrar.messenger(), "mopub_flutter")
      defaultChannel.setMethodCallHandler(MopubFlutterPlugin(registrar.context()))
      registrar
              .platformViewRegistry()
              .registerViewFactory("mopub_flutter/banner", MopubBannerFactory(
                      registrar.messenger(),
                      registrar.activity()
              ))

    }

  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    when(call.method) {
      "initialize" -> initialize(call.argument("adUnitId") ?: "")
      else -> result.notImplemented()
    }
  }

  private fun initialize(adUnitId: String) {
    val builder = SdkConfiguration.Builder(adUnitId)
    if (BuildConfig.DEBUG) {
      builder.withLogLevel(MoPubLog.LogLevel.DEBUG)
    } else {
      builder.withLogLevel(MoPubLog.LogLevel.INFO)
    }
    builder.withAdditionalNetwork("com.mopub.mobileads.AmazonAdapterConfiguration")
    val config = builder.build()
    MoPub.initializeSdk(context, config) {
      Log.d(TAG, "MoPub sdk initialized")
    }
  }

}
