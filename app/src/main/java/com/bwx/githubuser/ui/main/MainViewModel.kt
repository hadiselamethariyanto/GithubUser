package com.bwx.githubuser.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.map
import com.bwx.githubuser.domain.usecase.GithubUseCase
import com.bwx.githubuser.utils.DataMapper
import kotlinx.coroutines.flow.map

class MainViewModel(private val repository: GithubUseCase) : ViewModel() {

    fun getUsers() = repository.getPagingUsers().asLiveData()

    fun getDetailUser(login: String) = repository.getDetailUser(login).asLiveData()
}