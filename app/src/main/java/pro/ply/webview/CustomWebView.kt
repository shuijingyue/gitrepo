package pro.ply.webview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
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
        settings.setAppCacheEnabled(true)
        settings.loadsImagesAutomatically = true
        settings.setSupportMultipleWindows(false)
        settings.blockNetworkImage = false //是否阻塞加载网络图片  协议http or https

        settings.allowFileAccess = true //允许加载本地文件html  file协议

        settings.setAllowFileAccessFromFileURLs(false) //通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
        settings.setAllowUniversalAccessFromFileURLs(false) //允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
        settings.setJavaScriptCanOpenWindowsAutomatically(true)
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
        settings.setSavePassword(false)
        settings.setSaveFormData(false)
        settings.setLoadWithOverviewMode(true)
        settings.setUseWideViewPort(true)
        settings.setDomStorageEnabled(true)
        settings.setNeedInitialFocus(true)
        settings.setDefaultTextEncodingName("utf-8") //设置编码格式

        settings.setDefaultFontSize(16)
        settings.setMinimumFontSize(10) //设置 WebView 支持的最小字体大小，默认为 8

        settings.setGeolocationEnabled(true)
        settings.setUseWideViewPort(true)

        val appCacheDir: String =
            context.getDir("cache", Context.MODE_PRIVATE).path
        Log.i("WebSetting", "WebView cache dir: $appCacheDir")
        settings.setDatabasePath(appCacheDir)
        settings.setAppCachePath(appCacheDir)
        settings.setAppCacheMaxSize((1024 * 1024 * 80).toLong())
        setWebContentsDebuggingEnabled(true)

        addJavascriptInterface(this, "nativeBridge")
        methods.set("toast", ToastNativeBridgeMethod())
    }

    @JavascriptInterface
    fun postMessage(message: String) {
        val json = Gson().fromJson(message, JsonObject::class.java)

        val command = json.get("method")?.asString
        if (command.isNullOrEmpty()) return

        val params = json.get("params")?.asJsonObject
        val nativeMethod = methods.get(command) ?: return
        val res = nativeMethod.invoke(context, params)
        val callback = json.get("callback")?.asString

        if (res is JsonObject && callback?.isNotEmpty() == true) {
            evaluateJavascript("javascript:nativeBridgeDelegate.saveMessage(${callback}, $res)", null)
        }
    }
}