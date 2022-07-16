package pro.ply.gitrepo.home.ui

import android.os.Bundle

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.ply.arch.ui.BaseFragment
import pro.ply.gitrepo.home.R
import pro.ply.gitrepo.home.databinding.FragmentHomeBinding
import pro.ply.gitrepo.home.vm.HomeViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val contentLayoutId: Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println(viewModel)
        MainScope().launch {
            val htmlText = withContext(Dispatchers.IO) run@ {
                return@run viewModel.htmlContent()
            }
            binding.index.text = htmlText
        }
    }
}
