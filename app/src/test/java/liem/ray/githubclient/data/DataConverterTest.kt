package liem.ray.githubclient.data

import liem.ray.githubclient.data.apiModel.EventApiModel
import liem.ray.githubclient.data.apiModel.UserApiModel
import liem.ray.githubclient.data.apiModel.UserDetailApiModel
import org.junit.Test
import kotlin.test.assertEquals

class DataConverterTest {
    private val userApiModel = UserApiModel(
        login = "raymond",
        id = 10L,
        nodeId = "nodeId",
        avatarUrl = "https://myavatarurl",
        gravatarId = "gravatarId",
        url = "url",
        htmlUrl = "https://github/raymond",
        followersUrl = "followersUrl",
        followingUrl = "followingUrl",
        gistsUrl = "gistsUrl",
        starredUrl = "starredUrl",
        subscriptionsUrl = "subscriptionsUrl",
        organizationUrl = "organizationUrl",
        reposUrl = "reposUrl",
        eventsUrl = "eventsUrl",
        receivedEventsUrl = "receivedEventsUrl",
        type = "type",
        userViewType = "userViewType",
        siteAdmin = true,
    )

    private val userData = UserData(
        login = "raymond",
        avatarUrl = "https://myavatarurl",
        id = 10L,
    )

    @Test
    fun `UserApiModel converts to UserData`() {
        assertEquals(expected = userData, actual = userApiModel.toData())
    }

    @Test
    fun `UserDetailApiModel converts to UserDetailData`() {
        val userDetailApiModel = UserDetailApiModel(
            login = "raymond",
            id = 10L,
            nodeId = "nodeId",
            avatarUrl = "https://avatarUrl",
            gravatarId = "gravatarId",
            url = "url",
            htmlUrl = "htmlUrl",
            followersUrl = "followersUrl",
            followingUrl = "followingUrl",
            gistsUrl = "gistsUrl",
            starredUrl = "starredUrl",
            subscriptionsUrl = "subscriptionsUrl",
            organizationUrl = "organizationUrl",
            reposUrl = "reposUrl",
            eventsUrl = "eventsUrl",
            receivedEventsUrl = "receivedEventsUrl",
            type = "type",
            userViewType = "userViewType",
            siteAdmin = true,
            name = "Ray Liem",
            company = "BitFlyer",
            blog = "blog",
            location = "Japan",
            email = "raysleith@gmail.com",
            hireable = true,
            bio = "Coding test for BitFlyer",
            twitterUsername = "twitterUsername",
            publicRepos = 10,
            publicGists = 20,
            followers = 14,
            following = 10,
            createdAt = "2022-01-11T00:37:44Z",
            updatedAt = "2024-01-11T00:37:44Z",
        )

        val userDetailData = userDetailApiModel.toData()

        assertEquals(
            expected = UserDetailData(
                avatarUrl = "https://avatarUrl",
                publicRepos = 10,
                publicGists = 20,
                name = "Ray Liem",
                location = "Japan",
                htmlUrl = "htmlUrl",
                following = 10,
                followers = 14,
                email = "raysleith@gmail.com",
                createdAt = "January 11, 2022",
                id = 10L,
                company = "BitFlyer",
                login = "raymond",
                bio = "Coding test for BitFlyer",
                twitterUsername = "twitterUsername",
            ),
            actual = userDetailData,
        )
    }

    @Test
    fun `EventApiModel converts to EventData`() {
        val eventApiModel = EventApiModel(
            id = "id",
            type = "IssueCommentEvent",
            repo = EventApiModel.Repo(
                id = 1L,
                name = "GitHubClient",
            ),
            org = EventApiModel.Org(
                id = 10L,
                login = "BitFlyer",
                avatarUrl = "https://avatar/bitFlyer",
            ),
            payload = EventApiModel.Payload(
                action = "Created",
                ref = "ref",
                refType = "refType",
                description = "Description text here",
                pullRequest = EventApiModel.Payload.PullRequest(
                    url = "https://pullrequest",
                    title = "Pull request title",
                ),
                comment = EventApiModel.Payload.Comment(
                    id = 4L,
                    user = userApiModel,
                    body = "Payload comment here",
                ),
                release = EventApiModel.Payload.Release(
                    id = 100L,
                    name = "Release new version",
                ),
                review = EventApiModel.Payload.Review(
                    id = 34L,
                    body = "My review is here",
                    user = userApiModel,
                ),
                commits = listOf(
                    EventApiModel.Payload.Commit(
                        sha = "sha23434",
                        message = "Commit message",
                        url = "https://commit",
                    )
                )
            ),
        )

        val eventData = eventApiModel.toData()

        assertEquals(
            expected = EventData(
                repoName = "GitHubClient",
                type = EventType.IssueCommentEvent,
                payload = EventData.PayloadData(
                    action = "Created",
                    refType = "refType",
                    ref = "ref",
                    commits = listOf(
                        EventData.PayloadData.CommitData(sha = "sha23434", message = "Commit message", url = "https://commit")
                    ),
                    review = EventData.PayloadData.ReviewData(body = "My review is here", user = userData),
                    release = EventData.PayloadData.ReleaseData(name = "Release new version"),
                    pullRequest = EventData.PayloadData.PullRequestData(
                        title = "Pull request title",
                        url = "https://pullrequest"
                    ),
                    description = "Description text here",
                    comment = EventData.PayloadData.CommentData(comment = "Payload comment here", user = userData),
                ),
                orgName = "BitFlyer",
            ),
            actual = eventData,
        )
    }
}