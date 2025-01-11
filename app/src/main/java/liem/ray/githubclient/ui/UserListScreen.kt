package liem.ray.githubclient.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import liem.ray.githubclient.R
import liem.ray.githubclient.ui.common.BaseScreen

@Composable
fun UserListScreen(navController: NavController) {
    BaseScreen(title = stringResource(id = R.string.app_name), onBackClick = null) {
        Content()
    }
}

@Composable
private fun Content() {
    Column {
        Text(text = "Test")
    }
}