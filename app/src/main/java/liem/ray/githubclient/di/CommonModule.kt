package liem.ray.githubclient.di

import androidx.navigation.NavController
import liem.ray.githubclient.navigation.NavigatorService
import org.koin.dsl.module

val commonModule = module {
    factory { (navController: NavController) -> NavigatorService(navController = navController) }
}