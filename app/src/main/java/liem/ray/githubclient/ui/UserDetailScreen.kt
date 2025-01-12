package liem.ray.githubclient.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    BaseScreen(title = state.title, onBackClick = null) {

    }
    state.dialogItem?.let { showDialog(it) }
}