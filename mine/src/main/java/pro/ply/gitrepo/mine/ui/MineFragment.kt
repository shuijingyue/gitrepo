package pro.ply.gitrepo.mine.ui

import pro.ply.arch.ui.BaseFragment
import pro.ply.gitrepo.mine.R
import pro.ply.gitrepo.mine.databinding.FragmentMineBinding
import pro.ply.gitrepo.mine.vm.MineViewModel

class MineFragment : BaseFragment<MineViewModel, FragmentMineBinding>() {
    override val contentLayoutId: Int = R.layout.fragment_mine
}