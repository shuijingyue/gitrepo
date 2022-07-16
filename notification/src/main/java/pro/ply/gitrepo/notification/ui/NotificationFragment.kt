package pro.ply.gitrepo.notification.ui

import pro.ply.arch.ui.BaseFragment
import pro.ply.gitrepo.mylibrary.R
import pro.ply.gitrepo.mylibrary.databinding.FragmentNotificationBinding
import pro.ply.gitrepo.notification.vm.NotificationViewModel

class NotificationFragment : BaseFragment<NotificationViewModel, FragmentNotificationBinding>() {
    override val contentLayoutId: Int = R.layout.fragment_notification
}