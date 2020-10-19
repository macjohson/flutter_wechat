import Flutter
import UIKit
import WechatOpenSDK

public class SwiftFlutterWechatPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_wechat", binaryMessenger: registrar.messenger())
    let instance = SwiftFlutterWechatPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    if(call.method == "shareMiniProgress"){
        if let args = call.arguments as? Dictionary<String, Any>,
           let name = args["name"] as? String,
           let thumb = args["thumb"] as? FlutterStandardTypedData
           {
           
            let miniProgramObject: WXMiniProgramObject = WXMiniProgramObject();
            miniProgramObject.webpageUrl = "http://www.yixingdiandian.cn"
            miniProgramObject.path = "pages/index/index"
            miniProgramObject.miniProgramType = WXMiniProgramType.release
            miniProgramObject.userName = "gh_42c6bf7471a6"
            
            let msg: WXMediaMessage = WXMediaMessage();
            msg.title = "易行司机\(name)为您服务"
            msg.description = "点击与该司机预约用车"
            msg.thumbData = thumb.data
            
            let req: SendMessageToWXReq = SendMessageToWXReq();
            req.message = msg;
            
            req.scene = 0;
            
            WXApi.send(req)
           }
           
    }
    result("iOS " + UIDevice.current.systemVersion)
  }
}
