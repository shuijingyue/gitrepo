package pro.ply.webview

import android.content.Context
import android.widget.Toast
import com.google.gson.JsonObject

class ToastNativeBridgeMethod : NativeBridgeMethod {
    override fun invoke(context: Context, params: JsonObject?): Any {
        val text = params?.get("text")?.asString ?: return Unit
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        return Unit
    }
}