package pro.ply.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.abs

class DraggableTextView(
    context: Context, attrs: AttributeSet
) : AppCompatTextView(context, attrs) {
    private var draggable: Boolean = false
    private var startDragging = false
    private var dragCallback: DragCallback? = null

    private var pendingLongClickAction: Runnable? = null
    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private var clickCanceled: Boolean = false

    fun setDragCallback(callback: DragCallback) {
        dragCallback = callback
    }

    fun setDraggable(value: Boolean) {
        draggable = value
    }

    private var eventX = 0
    private var eventY = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!draggable || dragCallback == null)
            return super.onTouchEvent(event)

        val x = event.x
        val y = event.y

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                checkForPendingLongClick(x, y)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                if (startDragging) {
                    dragCallback?.onDragging(event.rawX, event.rawY)
                } else if (!pointerInView(x, y)) {
                    // 手指一旦划出view的范围，再划进来也不再执行click
                    clickCanceled = true
                    // 手指一旦划出view的范围，取消长按事件
                    removePendingLongClickAction()
                } else if (abs(x - eventX) > touchSlop || abs(y - eventY) > touchSlop) {
                    // 在触发长按事件之前移动手指，取消长按事件
                    clickCanceled = true
                }
                return true
            }

            MotionEvent.ACTION_UP -> {
                removePendingLongClickAction()
                if (startDragging) {
                    startDragging = false
                    dragCallback?.onDragEnd()
                } else if (!clickCanceled && pointerInView(x, y)) {
                    performClick()
                }
                clickCanceled = false
                return true
            }
        }

        return super.onTouchEvent(event)
    }

    private fun pointerInView(x: Float, y: Float): Boolean {
        return x > 0 && y > 0 && x < width && y < height
    }

    private fun checkForPendingLongClick(offsetX: Float, offsetY: Float) {
        if (pendingLongClickAction == null)
            pendingLongClickAction = PendingLongClickAction(floatArrayOf(offsetX, offsetY))
        postDelayed(
            pendingLongClickAction,
            ViewConfiguration.getLongPressTimeout().toLong()
        )
    }

    private inner class PendingLongClickAction(val offset: FloatArray) : Runnable {
        override fun run() {
            startDragging = true
            dragCallback?.onDragStart(this@DraggableTextView, offset)
        }
    }

    private fun removePendingLongClickAction() {
        if (pendingLongClickAction != null) {
            removeCallbacks(pendingLongClickAction)
            pendingLongClickAction = null
        }
    }

    interface DragCallback {
        fun onDragStart(itemView: DraggableTextView, offset: FloatArray)
        fun onDragging(x: Float, y: Float)
        fun onDragEnd()
    }

    companion object {
        private const val TAG = "DraggableTextView"
    }
}