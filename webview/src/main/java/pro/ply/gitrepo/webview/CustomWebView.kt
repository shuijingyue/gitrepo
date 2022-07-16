package pro.ply.gitrepo.webview

import android.content.Context
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import com.google.gson.Gson
import com.google.gson.JsonObject

class CustomWebView(context: Context, attrs: AttributeSet) : WebView(context, attrs) {
    private val methods = HashMap<String, NativeBridgeMethod>()

    init {
        enableSlowWholeDocumentDraw()
        settings.javaScriptEnabled = true
        settings.setSupportZoom(true)
        settings.builtInZoomControls = false

        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.textZoom = 100
        settings.databaseEnabled = true
        settings.loadsImagesAutomatically = true
        settings.setSupportMultipleWindows(false)
        settings.blockNetworkImage = false //是否阻塞加载网络图片  协议http or https

        settings.allowFileAccess = true //允许加载本地文件html  file协议

        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.domStorageEnabled = true
        settings.setNeedInitialFocus(true)
        settings.defaultTextEncodingName = "utf-8" //设置编码格式

        settings.defaultFontSize = 16
        settings.minimumFontSize = 10 //设置 WebView 支持的最小字体大小，默认为 8

        settings.setGeolocationEnabled(true)
        settings.useWideViewPort = true

        setWebContentsDebuggingEnabled(true)

        addJavascriptInterface(this, "nativeBridge")
        methods["toast"] = ToastNativeBridgeMethod()
    }

    @JavascriptInterface
    fun postMessage(message: String) {
        val json = Gson().fromJson(message, JsonObject::class.java)

        val command = json.get("method")?.asString
        if (command.isNullOrEmpty()) return

        val params = json.get("params")?.asJsonObject
        val nativeMethod = methods[command] ?: return
        val res = nativeMethod.invoke(context, params)
        val callback = json.get("callback")?.asString

        if (res is JsonObject && callback?.isNotEmpty() == true) {
            evaluateJavascript("javascript:nativeBridgeDelegate.saveMessage(${callback}, $res)", null)
        }
    }
}