
import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/services.dart';

class FlutterWechat {
  static const MethodChannel _channel =
      const MethodChannel('flutter_wechat');


  static Future<void> shareDriverMiniProgram(Uint8List thumb) async{
    await _channel.invokeMethod("shareMiniProgress", {
      "thumb": thumb
    });
  }
}
