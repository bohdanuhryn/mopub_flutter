import 'dart:async';

import 'package:flutter/services.dart';
import 'package:mopub_flutter/src/banner/mopub_banner_event.dart';

abstract class MopubBannerEventHandler {

    final Function(MopubBannerEvent, Map<String, dynamic>) listener;

    MopubBannerEventHandler(this.listener);

    Future<dynamic> handleEvent(MethodCall call) async {
        switch (call.method) {
            case 'loaded':
                listener(MopubBannerEvent.loaded, null);
                break;
            case 'failed':
                listener(MopubBannerEvent.failed, Map<String, dynamic>.from(call.arguments));
                break;
            case 'clicked':
                listener(MopubBannerEvent.clicked, null);
                break;
            case 'collapsed':
                listener(MopubBannerEvent.collapsed, null);
                break;
            case 'expanded':
                listener(MopubBannerEvent.expanded, null);
                break;
        }
        return null;
    }
}
