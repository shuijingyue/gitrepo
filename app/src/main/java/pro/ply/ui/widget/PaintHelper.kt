package pro.ply.ui.widget

import android.graphics.Paint

/**
 * 重置Paint
 */
fun Paint.applyDefaultStyle() {
    reset()
    isAntiAlias = true
}
