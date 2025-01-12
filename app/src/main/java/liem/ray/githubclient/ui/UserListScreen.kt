package liem.ray.githubclient.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin
import liem.ray.githubclient.R
import liem.ray.githubclient.data.UserData
import liem.ray.githubclient.navigation.NavigatorService
import liem.ray.githubclient.ui.common.BaseScreen
import liem.ray.githubclient.ui.components.showDialog
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UserListScreen(navigatorService: NavigatorService) {
    val viewModel = getViewModel<UserListViewModel> { parametersOf(navigatorService) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Content(state = state, actionHandler = viewModel)
}

@Composable
private fun Content(
    state: UserListViewModel.State,
    actionHandler: UserListViewModelActionHandler,
) {
    val listState = rememberLazyListState()
    BaseScreen(title = stringResource(id = R.string.app_name), onBackClick = null) {
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            items(items = state.users) {
                UserRow(
                    user = it,
                    onClick = actionHandler::onUserClick,
                )
            }
        }
    }

    state.dialogItem?.let { showDialog(it) }
}


@Composable
private fun UserRow(user: UserData, onClick: (UserData) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = true, onClick = { onClick(user) }),
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                imageModel = { user.avatarUrl },
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
            Text(user.login)
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewContent() {
    Content(
        state = UserListViewModel.State(
            users = List(size = 10) {
                UserData(login = "User $it", avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4", id = 1)
            },
        ),
        actionHandler = object : UserListViewModelActionHandler {},
    )
}


@Composable
@Preview(showBackground = true)
private fun PreviewUserRow() {
    UserRow(
        user = UserData(login = "Raymond", avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4", id = 1),
        onClick = {},
    )
}
