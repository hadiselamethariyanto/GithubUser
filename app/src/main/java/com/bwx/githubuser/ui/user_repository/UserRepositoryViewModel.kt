package com.bwx.githubuser.ui.user_repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.githubuser.domain.usecase.GithubUseCase

class UserRepositoryViewModel(private val repository: GithubUseCase) : ViewModel() {

    fun getUserRepository(login: String) = repository.getUserRepository(login).asLiveData()
}