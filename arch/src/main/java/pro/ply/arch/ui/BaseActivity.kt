package pro.ply.arch.ui

import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<ViewModel : androidx.lifecycle.ViewModel,
        ViewDataBinding : androidx.databinding.ViewDataBinding> : AppCompatActivity() {

    protected abstract val contentLayoutId: Int

    protected lateinit var binding: ViewDataBinding

    protected val viewModel: ViewModel by lazy {
        val type = javaClass.genericSuperclass as ParameterizedType
        val klass = type.actualTypeArguments[0] as Class<ViewModel>
        ViewModelProvider(this).get(klass)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyWindowInsets()

        binding =
            DataBindingUtil.setContentView(this, contentLayoutId)
    }

    private fun applyWindowInsets() {
        findViewById<View>(android.R.id.content).setOnApplyWindowInsetsListener out@{ v, insets ->
            v.setPadding(
                v.paddingLeft,
                insets.getInsets(WindowInsets.Type.statusBars()).top,
                v.paddingRight,
                v.paddingBottom
            )
            return@out insets
        }
    }
}