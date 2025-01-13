package liem.ray.githubclient.api

import liem.ray.githubclient.data.apiModel.EventApiModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApi {
    @GET("users/{username}/events/public")
    suspend fun getEvents(
        @Path("username") username: String,
        @Query("page") page: Int = 1,
        @Query("per_page") pageSize: Int = 10,
    ): List<EventApiModel>
}