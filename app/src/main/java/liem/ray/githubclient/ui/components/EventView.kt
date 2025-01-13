package liem.ray.githubclient.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import liem.ray.githubclient.R
import liem.ray.githubclient.data.EventData
import liem.ray.githubclient.data.EventType

@Composable
fun EventView(event: EventData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp),
    ) {
        Text(event.repoName, color = Color.Blue)
        if (event.orgName.isNotEmpty()) {
            IconTextView(iconResId = R.drawable.ic_org, text = event.orgName, iconSize = 12.dp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        when (event.type) {
            EventType.IssueCommentEvent -> IssueCommentEventRow(event = event)
            EventType.PushEvent -> PushEventRow(event = event)
            EventType.DeleteEvent -> DeleteEventRow(event = event)
            EventType.PullRequestEvent -> PullRequestEventRow(event = event)
            EventType.WatchEvent -> WatchEventRow(event = event)
            EventType.CreateEvent -> CreateEventRow(event = event)
            EventType.ReleaseEvent -> ReleaseEventRow(event = event)
            EventType.CommitCommentEvent -> CommitCommentEventRow(event = event)
            EventType.PullRequestReviewEvent -> PullRequestReviewEventRow(event = event)
            EventType.PullRequestReviewCommentEvent -> PullRequestReviewCommentEventRow(event = event)
            EventType.Unknown -> UnhandledEventRow()
        }
    }
}

@Composable
private fun IssueCommentEventRow(event: EventData) {
    val comment = event.payload?.comment ?: return
    Row {
        AvatarView(avatarUrl = comment.user?.avatarUrl, size = 30.dp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(comment.comment, color = Color.DarkGray)
    }
}

@Composable
private fun CreateEventRow(event: EventData) {
    val payload = event.payload ?: return
    Column {
        Text(
            text = "Created ${payload.refType} ${payload.ref}",
            color = Color.DarkGray,
        )
        if (payload.description.isNotEmpty()) {
            Text(
                text = payload.description,
                color = Color.Black,
            )
        }
    }
}


@Composable
private fun CommitCommentEventRow(event: EventData) {
    val comment = event.payload?.comment ?: return
    Row {
        AvatarView(avatarUrl = comment.user?.avatarUrl, size = 30.dp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = comment.comment, color = Color.DarkGray)
    }
}

@Composable
private fun ReleaseEventRow(event: EventData) {
    val payload = event.payload ?: return
    Text(text = "${payload.action} ${payload.release?.name}", color = Color.DarkGray)
}

@Composable
private fun PushEventRow(event: EventData) {
    val payload = event.payload ?: return
    Text(text = "Pushed ${payload.commits.size} commits", color = Color.DarkGray)
}

@Composable
private fun DeleteEventRow(event: EventData) {
    val payload = event.payload ?: return
    Text(text = "Deleted ${payload.refType}: ${payload.ref}", color = Color.DarkGray)
}

@Composable
private fun PullRequestEventRow(event: EventData) {
    val payload = event.payload ?: return
    val pullRequest = payload.pullRequest ?: return
    Column {
        PullRequestIconTextView(pullRequest = pullRequest)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = payload.action, color = Color.DarkGray)
    }
}

@Composable
private fun PullRequestReviewEventRow(event: EventData) {
    val payload = event.payload ?: return
    val pullRequest = payload.pullRequest ?: return;
    val review = payload.review ?: return
    Column {
        PullRequestIconTextView(pullRequest = pullRequest)
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            AvatarView(avatarUrl = review.user?.avatarUrl, size = 30.dp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = review.body, color = Color.DarkGray)
        }
    }
}

@Composable
private fun PullRequestReviewCommentEventRow(event: EventData) {
    val payload = event.payload ?: return
    val pullRequest = payload.pullRequest ?: return
    val comment = payload.comment ?: return
    Column {
        PullRequestIconTextView(pullRequest = pullRequest)
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            AvatarView(avatarUrl = comment.user?.avatarUrl, size = 30.dp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = comment.comment, color = Color.DarkGray)
        }
    }
}

@Composable
private fun WatchEventRow(event: EventData) {
    val payload = event.payload ?: return
    Text(text = "${payload.action} to watch", color = Color.DarkGray)
}

@Composable
private fun UnhandledEventRow() {
    Text(text = "Not supported event", color = Color.Red)
}

@Composable
private fun PullRequestIconTextView(pullRequest: EventData.PayloadData.PullRequestData) {
    IconTextView(
        iconResId = R.drawable.ic_pull_request,
        text = pullRequest.title,
        textColor = Color.Magenta,
        iconSize = 12.dp,
        iconTintColor = Color.Magenta,
    )
}