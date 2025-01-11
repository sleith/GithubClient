package liem.ray.githubclient.navigation

sealed class Screens(val route: String) {
  data object UserListScreen : Screens("userListScreen")
}
