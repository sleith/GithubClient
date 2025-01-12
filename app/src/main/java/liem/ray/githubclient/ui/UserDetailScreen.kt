package liem.ray.githubclient.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin
import liem.ray.githubclient.R
import liem.ray.githubclient.data.UserDetailData
import liem.ray.githubclient.navigation.NavigatorService
import liem.ray.githubclient.ui.common.BaseScreen
import liem.ray.githubclient.ui.components.showDialog
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UserDetailScreen(username: String, navigatorService: NavigatorService) {
    val viewModel = getViewModel<UserDetailViewModel> { parametersOf(username, navigatorService) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Content(state = state, actionHandler = viewModel)
}

@Composable
private fun Content(
    state: UserDetailViewModel.State,
    actionHandler: UserDetailViewModelActionHandler,
) {
    BaseScreen(title = state.title, onBackClick = actionHandler::onBackClick) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            val userDetail = state.userDetail ?: return@BaseScreen
            HeaderView(userDetail = userDetail)
            StatView(userDetail = userDetail)
            HorizontalDivider()
        }
    }
    state.dialogItem?.let { showDialog(it) }
}

@Composable
private fun HeaderView(userDetail: UserDetailData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GlideImage(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            imageModel = { userDetail.avatarUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            component = rememberImageComponent {
                +PlaceholderPlugin.Failure(painterResource(id = R.drawable.ic_avatar))
            },
            previewPlaceholder = painterResource(id = R.drawable.ic_avatar),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(text = userDetail.login, style = TextStyle(color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(10.dp))
            if (userDetail.location.isNotEmpty()) {
                Text(text = userDetail.location, style = TextStyle(color = Color.White))
            }
            Text(
                text = stringResource(id = R.string.user_detail_joined_at, userDetail.createdAt),
                style = TextStyle(color = Color.White)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    tint = Color.White,
                    contentDescription = "email",
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = userDetail.email.ifEmpty { "-" }, style = TextStyle(color = Color.LightGray))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_link),
                    tint = Color.White,
                    contentDescription = "githubUrl",
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = userDetail.htmlUrl.ifEmpty { "-" }, style = TextStyle(color = Color.LightGray))
            }
        }
    }
}

@Composable
private fun StatView(userDetail: UserDetailData) {
    val verticalSpace = 6.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray)
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "${userDetail.followers}", style = TextStyle(color = Color.DarkGray))
            Spacer(modifier = Modifier.height(verticalSpace))
            Text(text = stringResource(id = R.string.common_followers), style = TextStyle(color = Color.DarkGray))
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "${userDetail.following}", style = TextStyle(color = Color.DarkGray))
            Spacer(modifier = Modifier.height(verticalSpace))
            Text(text = stringResource(id = R.string.common_following), style = TextStyle(color = Color.DarkGray))
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "${userDetail.publicRepos}", style = TextStyle(color = Color.DarkGray))
            Spacer(modifier = Modifier.height(verticalSpace))
            Text(text = stringResource(id = R.string.common_repositories), style = TextStyle(color = Color.DarkGray))
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "${userDetail.publicGists}", style = TextStyle(color = Color.DarkGray))
            Spacer(modifier = Modifier.height(verticalSpace))
            Text(text = stringResource(id = R.string.common_gists), style = TextStyle(color = Color.DarkGray))
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewContent() {
    Content(
        state = UserDetailViewModel.State(
            title = "Username",
            userDetail = userDetailPreviewData,
            dialogItem = null,
        ),
        actionHandler = object : UserDetailViewModelActionHandler {},
    )
}

@Composable
@Preview(showBackground = true)
private fun PreviewHeaderView() {
    HeaderView(userDetail = userDetailPreviewData)
}

@Composable
@Preview(showBackground = true)
private fun PreviewStatView() {
    StatView(userDetail = userDetailPreviewData)
}

private val userDetailPreviewData
    get() = UserDetailData(
        login = "ray",
        id = 1,
        avatarUrl = "",
        name = "Ray Liem",
        company = "BitFlyer",
        htmlUrl = "https://github.com/ray",
        location = "Saitama, Japan",
        email = "raysleith@gmail.com",
        bio = "Gonna enjoy this life :)",
        twitterUsername = "rayliem",
        publicRepos = 20,
        publicGists = 10,
        followers = 40,
        following = 50,
        createdAt = "20 January, 1999",
    )