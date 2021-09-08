package pro.ply.ui.screen

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pro.ply.R
import pro.ply.data.vm.IndexViewModel
import pro.ply.databinding.FragmentLifecycleBinding
import java.util.*
import kotlin.math.floor

class LifecycleFragment : Fragment(R.layout.fragment_lifecycle) {
    private var _binding: FragmentLifecycleBinding? = null
    private val binding: FragmentLifecycleBinding
        get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityViewModels<IndexViewModel>()
        _binding = DataBindingUtil.bind(view)

        binding.numbers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)

                Log.i("wxp", "scroll")
            }
        })
        binding.numbers.adapter = NumbersAdapter(MutableList(30){ it + 1 })
        binding.numbers.addItemDecoration(ItemDecorator())
        binding.numbers.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()

        Log.i("wxp", "${(binding.numbers.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()}")
    }

    class NumbersAdapter(
        private val data: MutableList<Int> = Collections.emptyList()
    ) : RecyclerView.Adapter<ViewHolder>() {
        private var layoutInflater: LayoutInflater? = null

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            if (layoutInflater == null)
                layoutInflater = LayoutInflater.from(parent.context)

            val view = layoutInflater!!.inflate(R.layout.textview, parent, false) as TextView

//            val lp = view.layoutParams as MarginLayoutParams
//            lp.topMargin = 40
//            view.layoutParams = lp

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val text = holder.itemView as TextView
            val background = GradientDrawable()
            background.cornerRadius = 80f
            background.setColor(randomColor())
            text.background = background
            text.text = data[position].toString()
        }

        override fun getItemCount(): Int {
            return data.size
        }

        private fun randomColor(): Int {
            return Color.parseColor("#FF${random255()}${random255()}${random255()}")
        }

        private fun random255(): String {
            return floor(Math.random() * 255).toInt().toString(16).padStart(2, '0')
        }
    }

    class ViewHolder(view: TextView) : RecyclerView.ViewHolder(view) {
    }

    class ItemDecorator : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            if (position > 0) {
                outRect.top = 0
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
