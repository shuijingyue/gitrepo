package pro.ply.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import pro.ply.R

class WorksheetDatelineCell(context: Context, attrs: AttributeSet) :
    LinearLayoutCompat(context, attrs) {

    private var weekView: TextView
    private var dateView: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.worksheet, this) as ViewGroup
        weekView = view.getChildAt(0) as TextView
        dateView = view.getChildAt(1) as TextView
    }

    fun setDate(timestamp: Long) {
        tag = timestamp
    }
}