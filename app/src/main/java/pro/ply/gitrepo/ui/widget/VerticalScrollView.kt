package pro.ply.gitrepo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

class VerticalScrollView(context: Context, attrs: AttributeSet) : NestedScrollView(context, attrs) {
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.i(TAG, "interceptTouchEvent start")
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            super.onInterceptTouchEvent(ev)
            return false
        }
        Log.i(TAG, "interceptTouchEvent end")
        return true
    }

    companion object {
        private const val TAG = "VerticalScrollView"
    }
}