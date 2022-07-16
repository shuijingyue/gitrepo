package pro.ply.gitrepo.home.vm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.ply.gitrepo.home.api.HomeRequest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var homeRequest: HomeRequest

    suspend fun htmlContent(): String {
        return homeRequest.request()
    }
}