package pro.ply.gitrepo.home.api

import pro.ply.arch.net.RemoteRepository
import javax.inject.Inject

class HomeRequest @Inject constructor() : RemoteRepository<HomeService>() {

    suspend fun request(): String {
        return service.home().string()
    }
}