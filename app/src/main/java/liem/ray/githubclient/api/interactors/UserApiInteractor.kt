package liem.ray.githubclient.api.interactors

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import liem.ray.githubclient.api.UserApi
import liem.ray.githubclient.data.toData

class UserApiInteractor(private val userApi: UserApi) {
    suspend fun getUserList(since: Long? = null) = withContext(Dispatchers.IO) {
        Result.runCatching {
            val userList = userApi.getUsers(since)
            userList.map { it.toData() }
        }
    }

    suspend fun getUserDetail(username: String) = withContext(Dispatchers.IO) {
        Result.runCatching {
            val userDetail = userApi.getUserDetail(username = username)
            userDetail.toData()
        }
    }
}