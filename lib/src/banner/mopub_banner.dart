import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:mopub_flutter/src/banner/mopub_banner_controller.dart';
import 'package:mopub_flutter/src/banner/mopub_banner_event.dart';
import 'package:mopub_flutter/src/banner/mopub_banner_size.dart';

class MopubBanner extends StatefulWidget {

    final String adUnitId;
    final MopubBannerSize adSize;
    final void Function(MopubBannerEvent, Map<String, dynamic>?)? listener;
    final void Function(MopubBannerController?)? onBannerCreated;

    MopubBanner({
        Key? key,
        required this.adUnitId,
        required this.adSize,
        this.listener,
        this.onBannerCreated,
    }) : super(key: key);

    @override
    _MopubBannerState createState() => _MopubBannerState();

}

class _MopubBannerState extends State<MopubBanner> {

    MopubBannerController? _controller;

    @override
    Widget build(BuildContext context) {
        if (defaultTargetPlatform == TargetPlatform.android) {
            return Container(
                width: widget.adSize.width! >= 0 ? widget.adSize.width!.toDouble() : double.infinity,
                height: widget.adSize.height! >= 0 ? widget.adSize.height!.toDouble() : double.infinity,
                child: AndroidView(
                    key: UniqueKey(),
                    viewType: 'mopub_flutter/banner',
                    creationParams: <String, dynamic>{
                        "adUnitId": widget.adUnitId,
                        "adSize": widget.adSize.toMap
                    },
                    creationParamsCodec: StandardMessageCodec(),
                    onPlatformViewCreated: _onPlatformViewCreated
                )
            );
        } else if (defaultTargetPlatform == TargetPlatform.iOS) {
            return Container(
                key: UniqueKey(),
                width: widget.adSize.width!.toDouble(),
                height: widget.adSize.height!.toDouble(),
                child: UiKitView(
                    viewType: 'mopub_flutter/banner',
                    creationParams: <String, dynamic>{
                        "adUnitId": widget.adUnitId,
                        "adSize": widget.adSize.toMap,
                    },
                    creationParamsCodec: StandardMessageCodec(),
                    onPlatformViewCreated: _onPlatformViewCreated,
                )
            );
        } else {
            return Text('$defaultTargetPlatform is not yet supported by the plugin');
        }
    }

    @override
    void dispose() {
        super.dispose();
    }

    void _onPlatformViewCreated(int id) {
        _controller = MopubBannerController(id, widget.listener);
        if (widget.onBannerCreated != null) {
            widget.onBannerCreated!(_controller);
        }
    }

}
