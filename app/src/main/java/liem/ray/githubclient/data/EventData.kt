package liem.ray.githubclient.data

data class EventData(
    val repoName: String,
    val type: EventType,
    val orgName: String,
    val payload: PayloadData?,
) {
    data class PayloadData(
        val action: String,
        val ref: String,
        val refType: String,
        val description: String,
        val comment: CommentData?,
        val release: ReleaseData?,
        val commits: List<CommitData>,
        val pullRequest: PullRequestData?,
        val review: ReviewData?,
    ) {
        data class CommentData(
            val comment: String,
            val user: UserData?,
        )

        data class ReleaseData(
            val name: String,
        )

        data class CommitData(
            val sha: String,
            val message: String,
            val url: String,
        )

        data class PullRequestData(
            val title: String,
            val url: String,
        )

        data class ReviewData(
            val body: String,
            val user: UserData?,
        )
    }
}

enum class EventType(val apiValue: String) {
    IssueCommentEvent("IssueCommentEvent"),
    PushEvent("PushEvent"),
    DeleteEvent("DeleteEvent"),
    PullRequestEvent("PullRequestEvent"),
    WatchEvent("WatchEvent"),
    CreateEvent("CreateEvent"),
    ReleaseEvent("ReleaseEvent"),
    CommitCommentEvent("CommitCommentEvent"),
    PullRequestReviewEvent("PullRequestReviewEvent"),
    PullRequestReviewCommentEvent("PullRequestReviewCommentEvent"),
    Unknown(""),
    ;

    companion object {
        fun getType(apiValue: String): EventType {
            return entries.find { it.apiValue == apiValue } ?: Unknown
        }
    }
}