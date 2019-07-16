import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {

  const MethodChannel channel = MethodChannel('mopub_flutter');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {});
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

}
