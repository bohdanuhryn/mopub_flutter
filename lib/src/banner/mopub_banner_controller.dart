import 'package:flutter/services.dart';
import 'package:mopub_flutter/src/banner/mopub_banner_event.dart';
import 'package:mopub_flutter/src/banner/mopub_banner_event_handler.dart';

class MopubBannerController extends MopubBannerEventHandler {

    final MethodChannel _channel;

    MopubBannerController(int id, Function(MopubBannerEvent, Map<String, dynamic>) listener)
        : _channel = MethodChannel('mopub_flutter/banner_$id'), super(listener) {
        if (listener != null) {
            _channel.setMethodCallHandler(handleEvent);
        }
    }

    void dispose() {
        _channel.invokeMethod('dispose');
    }

}
