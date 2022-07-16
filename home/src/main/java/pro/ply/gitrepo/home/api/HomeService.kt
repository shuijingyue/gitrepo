package pro.ply.gitrepo.home.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {
    @GET("/")
    suspend fun home(): ResponseBody
}