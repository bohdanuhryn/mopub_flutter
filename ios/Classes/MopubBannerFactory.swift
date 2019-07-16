import Foundation
import Flutter

@objc class MopubBannerFactory : NSObject, FlutterPlatformViewFactory {
    
    let messeneger: FlutterBinaryMessenger
    
    init(messeneger: FlutterBinaryMessenger) {
        self.messeneger = messeneger
    }
    
    func create(withFrame frame: CGRect, viewIdentifier viewId: Int64, arguments args: Any?) -> FlutterPlatformView {
        return MopubBanner(
            frame: frame,
            viewId: viewId,
            args: args as? [String : Any] ?? [:],
            messeneger: messeneger
        )
    }
    
    func createArgsCodec() -> FlutterMessageCodec & NSObjectProtocol {
        return FlutterStandardMessageCodec.sharedInstance()
    }
    
}
