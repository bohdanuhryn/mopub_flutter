# mopub_flutter

MoPub plugin that shows banner ads using native platform views.

# Installation

## Android

#### Update `Manifest.xml`

~~~~
<meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
    android:value="ca-app-pub-3940256099942544~3347511713"/>

<meta-data
    android:name="applovin.sdk.key"
    android:value="**********" />
~~~~

## iOS

#### Update `Info.plist`

~~~~
<key>NSAppTransportSecurity</key>
<dict>
    <key>NSAllowsArbitraryLoads</key>
	<true/>
	<key>NSAllowsArbitraryLoadsForMedia</key>
	<true/>
	<key>NSAllowsArbitraryLoadsInWebContent</key>
	<true/>
</dict>
~~~~

~~~~
<key>GADApplicationIdentifier</key>
<string>ca-app-pub-3940256099942544~1458002511</string>
<key>io.flutter.embedded_views_preview</key>
<true/>
<key>AppLovinSdkKey</key>
<string>**********</string>
~~~~

# How to use

### Initialization

~~~~
import 'package:mopub_flutter/mopub_flutter.dart';

void main() {
  Mopub.initialize("xxxxxxxxxx");
  runApp(MyApp());
}
~~~~

### Banner

~~~~
MopubBanner(
  adUnitId: "xxxxxxxxxx",
  adSize: MopubBannerSize.BANNER
)
~~~~