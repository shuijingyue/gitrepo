package pro.ply.gitrepo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import kotlin.math.abs

class HorizontalScrollView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var lastEventX = 0
    private var lastEventY = 0

    private var startHorizontalScroll: Boolean = false
    private var startVerticalScroll: Boolean = false

    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop

    init {
        Log.i(TAG, "touchSlop $touchSlop")
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val x = ev.x.toInt()
        val y = ev.y.toInt()
        Log.i(TAG, "l,l ${lastEventX},${lastEventY}")
        Log.i(TAG, "x,y ${x},${y}")
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                Log.i(TAG, "down")
                lastEventX = x
                lastEventY = y
                startHorizontalScroll = false
                startVerticalScroll = false
                parent.requestDisallowInterceptTouchEvent(true)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.i(TAG, "move")
                if (startHorizontalScroll) {
                    Log.i(TAG, "startHorizontalScroll")
                } else if (startVerticalScroll) {
                    return false
                } else if (!startHorizontalScroll && !startVerticalScroll && abs(lastEventX - ev.x) > touchSlop) {
                    startHorizontalScroll = true
                } else if (!startVerticalScroll && !startHorizontalScroll && abs(lastEventY - ev.y) > touchSlop) {
                    startVerticalScroll = true
                    Log.i(TAG, "startVerticalScroll")
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            MotionEvent.ACTION_UP -> {

            }
        }

        lastEventX = x
        lastEventY = y

        return super.dispatchTouchEvent(ev)
    }

    companion object {
        private const val TAG = "HorizontalScrollView"
    }
}