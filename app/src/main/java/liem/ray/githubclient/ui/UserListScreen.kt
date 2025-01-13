package liem.ray.githubclient.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import liem.ray.githubclient.R
import liem.ray.githubclient.common.ui.OnBottomReached
import liem.ray.githubclient.data.UserData
import liem.ray.githubclient.navigation.NavigatorService
import liem.ray.githubclient.ui.common.BaseScreen
import liem.ray.githubclient.ui.components.AvatarView
import liem.ray.githubclient.ui.components.showDialog
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UserListScreen(navigatorService: NavigatorService) {
    val viewModel = getViewModel<UserListViewModel> { parametersOf(navigatorService) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Content(state = state, actionHandler = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    state: UserListViewModel.State,
    actionHandler: UserListViewModelActionHandler,
) {
    BaseScreen(title = stringResource(id = R.string.app_name), onBackClick = null) {
        PullToRefreshBox(isRefreshing = state.isRefreshing, onRefresh = actionHandler::onRefresh) {
            val listState = rememberLazyListState()
            LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                items(items = state.users) {
                    UserRow(
                        user = it,
                        onClick = actionHandler::onUserClick,
                    )
                }
            }
            listState.OnBottomReached { actionHandler.onLoadMore() }
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
            AvatarView(avatarUrl = user.avatarUrl, size = 50.dp)
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
