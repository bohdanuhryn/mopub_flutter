#import "MopubFlutterPlugin.h"
#import <mopub_flutter/mopub_flutter-Swift.h>

@implementation MopubFlutterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftMopubFlutterPlugin registerWithRegistrar:registrar];
}
@end
