package com.bwx.githubuser.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.githubuser.domain.usecase.GithubUseCase

class MainViewModel(private val repository: GithubUseCase) : ViewModel() {

    val users = repository.getPagingUsers()

    fun getDetailUser(login: String) = repository.getDetailUser(login).asLiveData()
}