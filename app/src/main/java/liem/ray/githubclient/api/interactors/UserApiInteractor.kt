package liem.ray.githubclient.api.interactors

import liem.ray.githubclient.api.UserApi
import liem.ray.githubclient.data.apiModel.UserApiModel
import liem.ray.githubclient.data.apiModel.UserDetailApiModel

class UserApiInteractor(private val userApi: UserApi) {
    suspend fun getUserList(since: Long? = null): List<UserApiModel> {
        return userApi.getUsers(since)
    }

    suspend fun getUserDetail(username: String): UserDetailApiModel {
        return userApi.getUserDetail(username = username)
    }
}