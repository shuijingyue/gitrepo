package pro.ply.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat
import pro.ply.R
import kotlin.math.abs
import kotlin.math.roundToInt

class Worksheet(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs), NestedScrollingChild3 {

    private val nestedScrollingChildHelper: NestedScrollingChildHelper =
        NestedScrollingChildHelper(this)

    private var firstVisibleChildIndex: Int = 1

    init {
        nestedScrollingChildHelper.isNestedScrollingEnabled = true
    }

    /**
     * 已经开始水平方向上的滑动
     */
    private var startHorizontalScrolling = false

    /**
     * 已经开始竖直方向上的滑动
     */
    private var startVerticalScrolling = false

    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop

    /**
     * [cellWidth]与当前view的宽度相关 在[onSizeChanged]中赋值
     * unit px
     */
    private var cellWidth: Int = 0

    /**
     * [cellHeight] 固定值
     * unit px
     */
    private val cellHeight: Int =
        resources.getDimensionPixelSize(R.dimen.designer_worksheet_cell_height)

    /**
     * unit px
     */
    private val cellSpace: Int = 7

    private val columnCount: Int = 3
    private val rowCount: Int = 24
    private val spaceCount: Int = columnCount - 1

    private var workGetter: WorkGetter? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        cellWidth =
            ((w.toFloat() - cellSpace * spaceCount) / columnCount).roundToInt()

        scrollTo(cellWidth + cellSpace, 0)

        offsetRightLimit = (cellWidth + spaceCount)
        offsetLeftLimit = -offsetRightLimit

        post {
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                val childLayoutParams = child.layoutParams as LayoutParams
                childLayoutParams.width = cellWidth + cellSpace
                childLayoutParams.height = LayoutParams.MATCH_PARENT
                childLayoutParams.marginStart = i * (cellWidth + cellSpace)
                child.setPadding(
                    child.paddingStart,
                    child.paddingTop,
                    cellSpace,
                    child.paddingBottom
                )
                child.layoutParams = childLayoutParams
            }

            initialWorksheet(0, { listOf() }) {}
        }
    }

    private var eventX: Int = 0
    private var eventY: Int = 0

    private val offset: IntArray = IntArray(2)

    private var offsetLeftLimit = 0
    private var offsetRightLimit = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.actionMasked) {

            MotionEvent.ACTION_DOWN -> {
                /* 暂时不用考虑长按事件 */
                eventX = x.toInt()
                eventY = y.toInt()
                startNestedScroll(
                    ViewCompat.SCROLL_AXIS_HORIZONTAL or ViewCompat.SCROLL_AXIS_VERTICAL,
                    ViewCompat.TYPE_TOUCH
                )
            }

            MotionEvent.ACTION_MOVE -> {

                val dx = (eventX - x).toInt()
                val dy = (eventY - y).toInt()

                eventX = x.toInt()
                eventY = y.toInt()

                if (!startVerticalScrolling && !startHorizontalScrolling) {
                    if (abs(dx) > touchSlop) {
                        startHorizontalScrolling = true
                    }
                    if (!startHorizontalScrolling && abs(dy) > touchSlop) {
                        startVerticalScrolling = true
                    }
                } else if (startVerticalScrolling) {
                    // 将滑动交给父容器处理
                    dispatchNestedScroll(
                        0,
                        0,
                        dx,
                        dy,
                        null,
                        ViewCompat.TYPE_TOUCH
                    )
                } else if (startHorizontalScrolling) {
                    scrollHorizontal(dx)
                }

                return true
            }

            MotionEvent.ACTION_UP -> {
                if (startHorizontalScrolling) {
                    startHorizontalScrolling = false
                } else if (startVerticalScrolling) {
                    startVerticalScrolling = false
                } else {
                    onClickCell(event.rawX.toInt(), event.rawY.toInt())
                }
            }
        }
        return true
    }

    /**
     * must be called
     */
    fun initialWorksheet(
        initialTimestamp: Long,
        workGetter: WorkGetter,
        onClickWorkListener: WorksheetCell.OnClickWorkListener
    ) {
        this.workGetter = workGetter
        for (i in 0 until childCount) {
            val child = getChildAt(i) as WorksheetCell
            child.setOnClickWorkListener(onClickWorkListener)
            val timestamp = initialTimestamp + i
            val works = workGetter.obtain(timestamp)
            child.fillWorks(timestamp, works)
        }
    }

    private fun scrollHorizontal(dx: Int) {
        /* 开始水平滚动 */
        val scrollOrientation = if (dx > 0) {
            SCROLL_ORIENTATION_RIGHT
        } else {
            SCROLL_ORIENTATION_LEFT
        }

        offset[0] += dx
        if (offset[0] > offsetRightLimit) {
            offsetLeftLimit += cellWidth + cellSpace
            offsetRightLimit += cellWidth + cellSpace
            val firstVisibleChild = getChildAt(firstVisibleChildIndex)
            val timestamp = firstVisibleChild.tag as Long

            /* move */
            val moveChildIndex = (firstVisibleChildIndex - 1 + 5) % 5
            val moveChild = getChildAt(moveChildIndex) as WorksheetCell
            val moveChildLayoutParams = moveChild.layoutParams as LayoutParams
            moveChildLayoutParams.marginStart += 5 * (cellWidth + cellSpace)
            moveChild.layoutParams = moveChildLayoutParams
            moveChild.fillWorks(
                timestamp + 4,
                workGetter?.obtain(timestamp + 4) ?: listOf()
            )

            firstVisibleChildIndex = (firstVisibleChildIndex + 1) % 5
        } else if (offset[0] < offsetLeftLimit) {
            offsetLeftLimit -= cellWidth + cellSpace
            offsetRightLimit -= cellWidth + cellSpace

            val firstVisibleChild = getChildAt(firstVisibleChildIndex)
            val timestamp = firstVisibleChild.tag as Long

            /* move */
            val moveChildIndex = (firstVisibleChildIndex + 3) % 5
            val moveChild = getChildAt(moveChildIndex) as WorksheetCell
            val moveChildLayoutParams = moveChild.layoutParams as LayoutParams
            moveChildLayoutParams.marginStart -= 5 * (cellWidth + cellSpace)
            moveChild.layoutParams = moveChildLayoutParams
            moveChild.fillWorks(timestamp - 2, listOf())

            firstVisibleChildIndex = (firstVisibleChildIndex - 1 + 5) % 5
        }

        scrollBy(dx, 0)
    }

    private fun onClickCell(rawX: Int, rawY: Int) {
        val rect = Rect()
        for (i in 0 until childCount) {
            val child = getChildAt(i) as WorksheetCell
            child.getGlobalVisibleRect(rect)
            if (rect.contains(rawX, rawY)) {
                child.onClickWorkRect(rawX, rawY)
                break
            }
        }
    }

    fun interface OnClickCellListener {
        fun onClickWorkRect(rawX: Int, rawY: Int)
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return nestedScrollingChildHelper.startNestedScroll(axes, type)
    }

    override fun stopNestedScroll(type: Int) {
        nestedScrollingChildHelper.startNestedScroll(type)
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        return nestedScrollingChildHelper.hasNestedScrollingParent(type)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int,
        consumed: IntArray
    ) {
        return nestedScrollingChildHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type,
            consumed
        )
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return nestedScrollingChildHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return nestedScrollingChildHelper.dispatchNestedPreScroll(
            dx,
            dy,
            consumed,
            offsetInWindow,
            type
        )
    }

    fun interface WorkGetter {
        fun obtain(timestamp: Long): List<Int>
    }

    companion object {
        private const val TAG = "Worksheet"
        private const val SCROLL_ORIENTATION_LEFT = 1
        private const val SCROLL_ORIENTATION_RIGHT = 2
    }
}