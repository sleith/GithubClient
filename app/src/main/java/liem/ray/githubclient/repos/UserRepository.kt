package liem.ray.githubclient.repos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import liem.ray.githubclient.api.interactors.UserApiInteractor
import liem.ray.githubclient.data.toData

class UserRepository(
    private val userApiInteractor: UserApiInteractor,
) {
    suspend fun getUserList(since: Long? = null) = withContext(Dispatchers.IO) {
        Result.runCatching {
            val userList = userApiInteractor.getUserList(since = since)
            userList.map { it.toData() }
        }
    }

    suspend fun getUserDetail(username: String) = withContext(Dispatchers.IO) {
        Result.runCatching {
            val user = userApiInteractor.getUserDetail(username = username)
            user.toData()
        }
    }
}