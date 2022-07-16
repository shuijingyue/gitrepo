package pro.ply.arch.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<ViewModel : androidx.lifecycle.ViewModel,
        ViewDataBinding : androidx.databinding.ViewDataBinding> :
    Fragment() {

    protected val viewModel: ViewModel by lazy {
        val type = javaClass.genericSuperclass as ParameterizedType
        val klass = type.actualTypeArguments[0] as Class<ViewModel>
        ViewModelProvider(this).get(klass)
    }

    protected abstract val contentLayoutId: Int

    protected lateinit var binding: ViewDataBinding

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            contentLayoutId, container, false
        )
        return binding.root
    }
}