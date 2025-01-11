package liem.ray.githubclient.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import liem.ray.githubclient.ui.components.CommonAppBar
import liem.ray.githubclient.ui.theme.GithubClientTheme

@Composable
fun BaseScreen(
    title: String?,
    onBackClick: (() -> Unit)?,
    content: @Composable () -> Unit,
) {
    GithubClientTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { CommonAppBar(title = title, onBackClick = onBackClick) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                content()
            }
        }
    }
}