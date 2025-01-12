package liem.ray.githubclient.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

class NavigatorService(
    private val navController: NavController
) {
    fun navigateToUserDetail(username: String) {
        navigate(Screens.UserDetailScreen.route + "/$username")
    }

    private fun navigate(
        route: String,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    ) {
        navController.navigate(route = route, navOptions = navOptions, navigatorExtras = navigatorExtras)
    }
}