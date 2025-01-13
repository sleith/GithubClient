package liem.ray.githubclient.data.apiModel

import androidx.annotation.Keep

@Keep
class EventApiModel(
    val id: String? = null,
    val type: String? = null,
    val actor: Actor? = null,
    val repo: Repo? = null,
    val payload: Payload? = null,
    val public: Boolean? = null,
    val createdAt: String? = null,
    val org: Org? = null,
) {
    @Keep
    class Actor(
        val id: Long? = null,
        val login: String? = null,
        val displayLogin: String? = null,
        val gravatarId: String? = null,
        val url: String? = null,
        val avatarUrl: String? = null,
    )

    @Keep
    class Payload(
        val action: String? = null,
        val ref: String? = null,
        val refType: String? = null,
        val description: String? = null,
        val pullRequest: PullRequest? = null,
        val comment: Comment? = null,
        val commits: List<Commit>? = null,
        val release: Release? = null,
        val review: Review? = null,
    ) {
        @Keep
        class PullRequest(
            val url: String? = null,
            val title: String? = null,
            val htmlUrl: String? = null,
            val diffUrl: String? = null,
            val patchUrl: String? = null,
            val mergedAt: String? = null
        )

        @Keep
        class Commit(
            val sha: String? = null,
            val message: String? = null,
            val url: String? = null,
        )

        @Keep
        class Comment(
            val id: Long? = null,
            val url: String? = null,
            val htmlUrl: String? = null,
            val issueUrl: String? = null,
            val nodeId: String? = null,
            val user: UserApiModel? = null,
            val createdAt: String? = null,
            val updatedAt: String? = null,
            val authorAssociation: String? = null,
            val body: String? = null,
            val performedViaGithubApp: String? = null,
        )

        @Keep
        class Release(
            val id: Long? = null,
            val name: String? = null,
            val url: String? = null
        )

        @Keep
        class Review(
            val id: Long? = null,
            val body: String? = null,
            val user: UserApiModel? = null,
        )
    }


    @Keep
    class Repo(
        val id: Long? = null,
        val name: String? = null,
        val url: String? = null
    )

    @Keep
    class Org(
        val id: Long? = null,
        val login: String? = null,
        val gravatarId: String? = null,
        val url: String? = null,
        val avatarUrl: String? = null,
    )
}