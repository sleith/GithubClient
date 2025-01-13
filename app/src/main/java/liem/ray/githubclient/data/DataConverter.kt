package liem.ray.githubclient.data

import liem.ray.githubclient.data.apiModel.EventApiModel
import liem.ray.githubclient.data.apiModel.UserApiModel
import liem.ray.githubclient.data.apiModel.UserDetailApiModel
import java.text.SimpleDateFormat
import java.util.Locale

fun UserApiModel.toData(): UserData {

    return UserData(
        login = login.orEmpty(),
        id = id ?: 0L,
        avatarUrl = avatarUrl.orEmpty(),
    )
}

fun UserDetailApiModel.toData(): UserDetailData {
    return UserDetailData(
        login = login.orEmpty(),
        id = id ?: 0L,
        avatarUrl = avatarUrl.orEmpty(),
        name = name.orEmpty(),
        htmlUrl = htmlUrl.orEmpty(),
        company = company.orEmpty(),
        location = location.orEmpty(),
        email = email.orEmpty(),
        bio = bio.orEmpty(),
        twitterUsername = twitterUsername.orEmpty(),
        publicRepos = publicRepos ?: 0,
        publicGists = publicGists ?: 0,
        followers = followers ?: 0,
        following = following ?: 0,
        createdAt = createdAt.orEmpty().run {
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val date = dateFormat.parse(this)

                val toDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
                toDateFormat.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        },
    )
}

fun EventApiModel.toData(): EventData {
    return EventData(
        repoName = repo?.name.orEmpty(),
        type = EventType.getType(type.orEmpty()),
        orgName = org?.login.orEmpty(),
        payload = payload?.toData(),
    )
}

private fun EventApiModel.Payload.toData(): EventData.PayloadData {
    return EventData.PayloadData(
        action = action.orEmpty(),
        ref = ref.orEmpty(),
        refType = refType.orEmpty(),
        description = description.orEmpty(),
        comment = comment?.let { comment ->
            EventData.PayloadData.CommentData(
                comment = comment.body.orEmpty(),
                user = comment.user?.toData(),
            )
        },
        release = release?.let { release -> EventData.PayloadData.ReleaseData(name = release.name.orEmpty()) },
        commits = commits?.map { commit ->
            EventData.PayloadData.CommitData(
                sha = commit.sha.orEmpty(),
                message = commit.message.orEmpty(),
                url = commit.url.orEmpty(),
            )
        } ?: emptyList(),
        pullRequest = pullRequest?.let { pullRequest ->
            EventData.PayloadData.PullRequestData(
                title = pullRequest.title.orEmpty(),
                url = pullRequest.url.orEmpty(),
            )
        },
        review = review?.let { review ->
            EventData.PayloadData.ReviewData(body = review.body.orEmpty(), user = review.user?.toData())
        }
    )
}