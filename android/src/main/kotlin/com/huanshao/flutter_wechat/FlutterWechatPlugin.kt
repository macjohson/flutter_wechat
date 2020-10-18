package com.huanshao.flutter_wechat

import android.content.Context
import androidx.annotation.NonNull;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** FlutterWechatPlugin */
public class FlutterWechatPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private val appId = "wxb203148e7e2bcc0c";
  private lateinit var api: IWXAPI;


  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_wechat")
    channel.setMethodCallHandler(this);
    api = WXAPIFactory.createWXAPI(flutterPluginBinding.applicationContext, appId);
    api.registerApp(appId);
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "flutter_wechat")
      channel.setMethodCallHandler(FlutterWechatPlugin())
    }
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "shareMiniProgress") {
      val bytes: ByteArray? = call.argument<ByteArray>("thumb");
      val miniProgramObj = WXMiniProgramObject();
      miniProgramObj.webpageUrl = "http://www.yixingdiandian.cn";
      miniProgramObj.path = "pages/index/index";
      miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;
      miniProgramObj.userName = "gh_42c6bf7471a6";

      val msg = WXMediaMessage(miniProgramObj);
      msg.title = "司机分享";
      msg.description = "点击直接打开司机行程";
      msg.thumbData = bytes;

      val req: SendMessageToWX.Req = SendMessageToWX.Req();
      req.message = msg;
      req.scene = SendMessageToWX.Req.WXSceneSession;
      api.sendReq(req)
      return
    }
    
    result.notImplemented()
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
