package com.bohdanuhryn.mopub_flutter_example

import android.content.Context
import android.support.multidex.MultiDex
import io.flutter.app.FlutterApplication

class App : FlutterApplication() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    
}
