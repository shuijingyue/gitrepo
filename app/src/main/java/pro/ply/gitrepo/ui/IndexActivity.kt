package pro.ply.gitrepo.ui

import android.os.Bundle
import android.view.*
import android.widget.TextView
import dagger.hilt.android.AndroidEntryPoint
import pro.ply.arch.ui.BaseActivity
import pro.ply.gitrepo.R
import pro.ply.gitrepo.databinding.ActivityIndexBinding
import pro.ply.gitrepo.vm.IndexViewModel

@AndroidEntryPoint
class IndexActivity : BaseActivity<IndexViewModel, ActivityIndexBinding>() {
    private val _tag = "IndexActivity"

    override val contentLayoutId: Int = R.layout.activity_index

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)

        with(binding.toolbar) {
            setContentInsetsAbsolute(0, 0)
            setPadding(20, paddingTop, 20, paddingBottom)
            setLogo(R.drawable.ic_baseline_explore_24)
            addView(TextView(this@IndexActivity).apply { text = "text" }, 0)
        }

        with(binding.collapsingToolbarLayout) {
            title = "home"
            setExpandedTitleTextAppearance(R.style.expandedActionBarTextAppearance)
            setCollapsedTitleTextAppearance(R.style.collapsingActionBarTextAppearance)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
