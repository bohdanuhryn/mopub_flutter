import Foundation
import Flutter

@objc class MopubBannerFactory : NSObject, FlutterPlatformViewFactory {
    
    let messeneger: FlutterBinaryMessenger
    let viewController: UIViewController
    
    init(messeneger: FlutterBinaryMessenger, viewController: UIViewController) {
        self.messeneger = messeneger
        self.viewController = viewController
    }
    
    func create(withFrame frame: CGRect, viewIdentifier viewId: Int64, arguments args: Any?) -> FlutterPlatformView {
        let arguments: [String:Any] = args as? [String : Any] ?? [:]
        return MopubBanner(
            frame: frame,
            viewId: viewId,
            args: arguments,
            messeneger: messeneger,
            viewController: viewController
        )
    }
    
    func createArgsCodec() -> FlutterMessageCodec & NSObjectProtocol {
        return FlutterStandardMessageCodec.sharedInstance()
    }
    
}
