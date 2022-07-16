package pro.ply.gitrepo.data.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import pro.ply.gitrepo.BR

data class JavaObject(val id: Int) : BaseObservable() {
    @Bindable
    var value: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.value)
        }
}