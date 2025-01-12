package liem.ray.githubclient.data

data class UserDetailData(
    val login: String,
    val id: Long,
    val avatarUrl: String,
    val name: String,
    val company: String,
    val htmlUrl: String,
    val location: String,
    val email: String,
    val bio: String,
    val twitterUsername: String,
    val publicRepos: Int,
    val publicGists: Int,
    val followers: Int,
    val following: Int,
    val createdAt: String,
)