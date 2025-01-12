package liem.ray.githubclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import liem.ray.githubclient.ui.UserDetailScreen
import liem.ray.githubclient.ui.UserListScreen
import org.koin.compose.rememberKoinInject
import org.koin.core.parameter.parametersOf

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val navigatorService = rememberKoinInject<NavigatorService> { parametersOf(navController) }
    NavHost(navController = navController, startDestination = Screens.UserListScreen.route) {
        composable(route = Screens.UserListScreen.route) {
            UserListScreen(navigatorService = navigatorService)
        }
        composable(
            route = Screens.UserDetailScreen.route + "/{username}",
            arguments = listOf(navArgument(name = "username") { type = NavType.StringType })
        ) { entry ->
            UserDetailScreen(
                username = entry.arguments?.getString("username").orEmpty(),
                navigatorService = navigatorService,
            )
        }
    }
}