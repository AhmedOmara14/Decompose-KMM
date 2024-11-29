package org.omaradev.kmp.di

import org.koin.dsl.module
import org.omaradev.kmp.HomeRepository
import org.omaradev.kmp.HomeViewModel
import org.omaradev.kmp.root.DefaultRootComponent
import org.omaradev.kmp.root.RootComponent

fun commonModule() = networkModule() + module {
    single<HomeRepository> {
        HomeRepository(get())
    }
    single<HomeViewModel> {
        HomeViewModel(get())
    }

    single<RootComponent> {
        DefaultRootComponent(
            componentContext = get(),
            homeViewModel = get()
        )
    }
}