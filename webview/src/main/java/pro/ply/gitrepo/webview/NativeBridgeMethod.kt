package pro.ply.gitrepo.webview

import android.content.Context
import com.google.gson.JsonObject

interface NativeBridgeMethod {
    fun invoke(context: Context, params: JsonObject?): Any?
}