import Foundation
import MoPub

@objc class MopubBanner : NSObject, FlutterPlatformView {
    
    private let viewController: UIViewController
    private let channel: FlutterMethodChannel
    private let messeneger: FlutterBinaryMessenger
    private let frame: CGRect
    private let viewId: Int64
    private let args: [String: Any]
    private var adView: MPAdView?
    
    init(frame: CGRect, viewId: Int64, args: [String: Any], messeneger: FlutterBinaryMessenger, viewController: UIViewController) {
        self.args = args
        self.messeneger = messeneger
        self.frame = frame
        self.viewId = viewId
        self.viewController = viewController
        channel = FlutterMethodChannel(name: "mopub_flutter/banner_\(viewId)", binaryMessenger: messeneger)
    }
    
    func view() -> UIView {
        return getBannerAdView() ?? UIView()
    }
    
    private func dispose() {
        adView?.removeFromSuperview()
        adView = nil
        channel.setMethodCallHandler(nil)
    }
    
    private func getBannerAdView() -> MPAdView? {
        if adView == nil {
            let adUnitId = self.args["adUnitId"] as? String ?? ""
            let adSize = self.args["adSize"] as? Dictionary<String, Any> ?? [String:Any]()
            let width = adSize["width"] as? Int ?? 0
            let height = adSize["height"] as? Int ?? 0
            adView = MPAdView(adUnitId: adUnitId, size: CGSize(width: width, height: height))
            adView!.frame = self.frame.width == 0 ? CGRect(x: 0, y: 0, width: 1, height: 1) : self.frame;
            channel.setMethodCallHandler { [weak self] (flutterMethodCall: FlutterMethodCall, flutterResult: FlutterResult) in
                switch flutterMethodCall.method {
                case "dispose":
                    self?.dispose()
                    break
                default:
                    flutterResult(FlutterMethodNotImplemented)
                }
            }
            adView?.localExtras = [
                "viewController":viewController,
                "adWidth":width,
                "adHeight":height
            ]
            adView!.loadAd()
        }
        
        return adView
    }
    
}
