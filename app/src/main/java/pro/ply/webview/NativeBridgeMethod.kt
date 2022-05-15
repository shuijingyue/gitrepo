package pro.ply.webview

import android.content.Context
import com.google.gson.JsonObject

interface NativeBridgeMethod {
    fun invoke(context: Context, params: JsonObject?): Any?
}