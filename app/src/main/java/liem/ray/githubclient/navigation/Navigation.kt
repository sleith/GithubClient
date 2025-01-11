package liem.ray.githubclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import liem.ray.githubclient.ui.UserListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.UserListScreen.route) {
        composable(route = Screens.UserListScreen.route) {
            UserListScreen(navController = navController)
        }
    }
}