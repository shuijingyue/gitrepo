package pro.ply.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class WorksheetCell(context: Context, attrs: AttributeSet): View(context, attrs), Worksheet.OnClickCellListener {

    private var works: List<Int> = listOf()

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var workRectMap: MutableMap<Rect, Int> = mutableMapOf()

    private var onClickWorkListener: OnClickWorkListener? = null

    fun fillWorks(timestamp: Long, works: List<Int>) {
        tag = timestamp
        this.works = works
        invalidate()
    }

    fun setOnClickWorkListener(listener: OnClickWorkListener) {
        onClickWorkListener = listener
    }

    override fun onClickWorkRect(rawX: Int, rawY: Int) {
        val location = IntArray(2)
        getLocationInWindow(location)
        val x = rawX - location[0]
        val y = rawY - location[1]
        for (entry in workRectMap.entries) {
            val rect = entry.key
            if (rect.contains(x, y)) {
                onClickWorkListener?.onClick(entry.value)
                break
            }
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        workRectMap.clear()
        paint.textSize = 64f
        canvas.drawText("${tag}", 50f, 50f, paint)
    }

    fun interface OnClickWorkListener {
        fun onClick(id: Int)
    }
}