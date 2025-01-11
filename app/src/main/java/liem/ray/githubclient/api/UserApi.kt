package liem.ray.githubclient.api

import liem.ray.githubclient.data.apiModel.UserApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("users")
    suspend fun getUsers(@Query("since") since: Long? = null): List<UserApiModel>
}