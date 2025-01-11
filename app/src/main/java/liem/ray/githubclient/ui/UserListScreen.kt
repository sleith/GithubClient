package liem.ray.githubclient.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import liem.ray.githubclient.R
import liem.ray.githubclient.navigation.NavigatorService
import liem.ray.githubclient.ui.common.BaseScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UserListScreen(navController: NavController) {
    val viewModel = getViewModel<UserListViewModel> { parametersOf(NavigatorService(navController)) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    BaseScreen(title = stringResource(id = R.string.app_name), onBackClick = null) {
        Content(state = state, actionHandler = viewModel)
    }
}

@Composable
private fun Content(
    state: UserListViewModel.State,
    actionHandler: UserListViewModelActionHandler
) {
    Column {
        Text(text = "Test")
    }
}