package liem.ray.githubclient.api

import liem.ray.githubclient.data.apiModel.UserApiModel
import liem.ray.githubclient.data.apiModel.UserDetailApiModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("users")
    suspend fun getUsers(@Query("since") since: Long? = null, @Query("per_page") pageSize: Int = 10): List<UserApiModel>

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): UserDetailApiModel
}