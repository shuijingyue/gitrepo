package pro.ply.ui.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import pro.ply.R
import pro.ply.data.bean.JavaObject
import pro.ply.data.vm.IndexViewModel
import pro.ply.databinding.ActivityIndexBinding

class IndexActivity : AppCompatActivity() {

    private val vm by lazy { ViewModelProvider(this).get(IndexViewModel::class.java) }

    private lateinit var binding: ActivityIndexBinding

    private val value = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_index)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, LifecycleFragment())
            .commit()
    }

    private fun hello(): String {
        return "Hello"
    }

    private external fun objectFromJNI(): JavaObject

    private external fun stringFromJNI(): String

    companion object {
        private const val TAG = "IndexActivity"

        init {
            System.loadLibrary("cplusplus");
        }
    }
}
