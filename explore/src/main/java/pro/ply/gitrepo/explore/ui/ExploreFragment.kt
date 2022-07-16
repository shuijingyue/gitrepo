package pro.ply.gitrepo.explore.ui

import pro.ply.arch.ui.BaseFragment
import pro.ply.gitrepo.explore.R
import pro.ply.gitrepo.explore.databinding.FragmentExploreBinding
import pro.ply.gitrepo.explore.vm.ExploreViewModel

class ExploreFragment : BaseFragment<ExploreViewModel, FragmentExploreBinding>() {
    override val contentLayoutId: Int = R.layout.fragment_explore
}
