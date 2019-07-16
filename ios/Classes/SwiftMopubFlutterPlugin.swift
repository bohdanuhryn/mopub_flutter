import Flutter
import MoPub

public class SwiftMopubFlutterPlugin: NSObject, FlutterPlugin {
    
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "mopub_flutter", binaryMessenger: registrar.messenger())
        let instance = SwiftMopubFlutterPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
        
        registrar.register(
            MopubBannerFactory(messeneger: registrar.messenger()),
            withId: "mopub_flutter/banner"
        )
    }
    
    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        switch call.method {
        case "initialize":
            let args = call.arguments as? Dictionary ?? [:]
            let adUnitId = args["adUnitId"] as? String ?? ""
            initialize(adUnitId: adUnitId)
            break
        default:
            result(FlutterMethodNotImplemented)
        }
    }
    
    private func initialize(adUnitId: String) {
        let sdkConfig = MPMoPubConfiguration(adUnitIdForAppInitialization: adUnitId)
        sdkConfig.globalMediationSettings = []
        sdkConfig.loggingLevel = MPBLogLevel.debug
        MoPub.sharedInstance().initializeSdk(with: sdkConfig) {
            print("SDK initialization complete")
        }
    }
    
}
