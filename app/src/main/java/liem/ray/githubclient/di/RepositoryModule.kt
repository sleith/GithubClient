package liem.ray.githubclient.di

import liem.ray.githubclient.repos.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { UserRepository(get()) }
}