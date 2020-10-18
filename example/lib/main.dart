import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter_wechat/flutter_wechat.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on'),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: (){
            ImageProvider provider = NetworkImage('https://qiniu.yixingdiandian.cn/dark.jpg');
            provider.obtainKey(createLocalImageConfiguration(context)).then((value) => provider.load(value, (bytes, {allowUpscaling, cacheHeight, cacheWidth}){
              FlutterWechat.shareDriverMiniProgram(bytes.buffer.asUint8List());
              return instantiateImageCodec(bytes, targetWidth: cacheWidth, targetHeight: cacheHeight);
            }));
          },
          child: Icon(Icons.share),
        ),
      ),
    );
  }
}
