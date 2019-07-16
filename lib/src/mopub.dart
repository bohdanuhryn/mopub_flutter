import 'package:flutter/services.dart';

class Mopub {

    Mopub.initialize(String appId) {
        MethodChannel _channel = const MethodChannel('mopub_flutter');
        _channel.invokeMethod('initialize', { "adUnitId" : appId });
    }

}