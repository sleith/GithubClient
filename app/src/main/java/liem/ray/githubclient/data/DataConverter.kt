package liem.ray.githubclient.data

import liem.ray.githubclient.data.apiModel.UserApiModel

fun UserApiModel.toData(): UserData {
    return UserData(
        login = login.orEmpty(),
        id = id ?: 0L,
        avatarUrl = avatarUrl.orEmpty(),
    )
}