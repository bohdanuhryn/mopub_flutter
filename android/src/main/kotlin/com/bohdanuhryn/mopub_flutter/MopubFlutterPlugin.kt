package com.bohdanuhryn.mopub_flutter

import android.content.Context
import android.util.Log
import com.mopub.common.MoPub
import com.mopub.common.SdkConfiguration
import com.mopub.common.logging.MoPubLog
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.platform.PlatformViewRegistry

class MopubFlutterPlugin : MethodCallHandler, ActivityAware, FlutterPlugin {

    private val TAG: String = MopubFlutterPlugin::class.java.simpleName
    private lateinit var platformViewRegistry: PlatformViewRegistry
    private lateinit var binaryMessenger: BinaryMessenger
    private lateinit var defaultChannel: MethodChannel

    companion object {

        @JvmStatic
        private lateinit var context: Context
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
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
        val config = builder.build()
        MoPub.initializeSdk(context, config) {
            Log.d(TAG, "MoPub sdk initialized")
        }
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        defaultChannel = MethodChannel(binaryMessenger, "mopub_flutter")
        defaultChannel.setMethodCallHandler(MopubFlutterPlugin())
        platformViewRegistry
                .registerViewFactory("mopub_flutter/banner", MopubBannerFactory(
                        binaryMessenger,
                        binding.activity
                ))
    }

    override fun onDetachedFromActivityForConfigChanges() {
        TODO("Not yet implemented")
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        TODO("Not yet implemented")
    }

    override fun onDetachedFromActivity() {
        TODO("Not yet implemented")
    }

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        platformViewRegistry = binding.platformViewRegistry
        binaryMessenger = binding.binaryMessenger
        context = binding.applicationContext
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        defaultChannel.setMethodCallHandler(null)
    }

}
