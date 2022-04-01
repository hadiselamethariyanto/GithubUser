package com.bwx.githubuser.di

import com.bwx.githubuser.domain.usecase.GithubInteractor
import com.bwx.githubuser.domain.usecase.GithubUseCase
import com.bwx.githubuser.ui.detail_user.DetailUserViewModel
import com.bwx.githubuser.ui.following.FollowingViewModel
import com.bwx.githubuser.ui.main.MainViewModel
import com.bwx.githubuser.ui.user_repository.UserRepositoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<GithubUseCase> { GithubInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailUserViewModel(get()) }
    viewModel { UserRepositoryViewModel(get()) }
    viewModel { FollowingViewModel(get()) }
}