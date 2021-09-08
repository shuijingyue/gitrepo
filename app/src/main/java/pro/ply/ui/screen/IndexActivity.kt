package pro.ply.ui.screen

import android.app.Instrumentation
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import pro.ply.R
import pro.ply.data.vm.IndexViewModel
import pro.ply.databinding.ActivityIndexBinding

class IndexActivity : AppCompatActivity() {

    private val vm by lazy { ViewModelProvider(this).get(IndexViewModel::class.java) }

    private lateinit var binding: ActivityIndexBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_index)
        binding.vm = vm
        val am = Instrumentation.ActivityMonitor()


    }

    companion object {
        private const val TAG = "IndexActivity"
    }
}
