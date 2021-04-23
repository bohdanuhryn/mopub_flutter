import 'dart:io';

import 'package:flutter/material.dart';

import 'package:mopub_flutter/mopub_flutter.dart';
import 'package:mopub_flutter_example/mopub_consts.dart';

void main() {
    WidgetsFlutterBinding.ensureInitialized();
    Mopub.initialize(Platform.isIOS ? MopubConsts.bannerIdIOS : MopubConsts.bannerIdAndroid);
    runApp(MopubApp());
}

class MopubApp extends StatefulWidget {

    @override
    _MopubAppState createState() => _MopubAppState();

}

class _MopubAppState extends State<MopubApp> {

    @override
    void initState() {
      super.initState();
    }

    @override
    Widget build(BuildContext context) {
        return MaterialApp(
            home: Scaffold(
                appBar: AppBar(
                    title: const Text('Mopub plugin example app'),
                ),
                body: Column(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
                        Center(child: Text("Banner")),
                        MopubBanner(
                            adUnitId: Platform.isIOS ? MopubConsts.bannerIdIOS : MopubConsts.bannerIdAndroid,
                            adSize: MopubBannerSize.BANNER
                        ),
                        Center(child: Text("Banner Medium")),
                        MopubBanner(
                            adUnitId: Platform.isIOS ? MopubConsts.bannerMiddleIdIOS : MopubConsts.bannerMiddleIdAndroid,
                            adSize: MopubBannerSize.MEDIUM_RECTANGLE
                        )
                    ]
                )
            )
        );
    }

}
