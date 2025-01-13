package liem.ray.githubclient.di

import liem.ray.githubclient.navigation.NavigatorService
import liem.ray.githubclient.ui.UserDetailViewModel
import liem.ray.githubclient.ui.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (navigator: NavigatorService) -> UserListViewModel(userApiInteractor = get(), navigator = navigator) }
    viewModel { (username: String, navigator: NavigatorService) ->
        UserDetailViewModel(
            username = username,
            userApiInteractor = get(),
            eventApiInteractor = get(),
            navigator = navigator
        )
    }
}