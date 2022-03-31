package com.bwx.githubuser.ui.detail_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.githubuser.domain.usecase.GithubUseCase

class DetailUserViewModel(private val repository: GithubUseCase) : ViewModel() {

    fun getDetailUser(login: String) = repository.getDetailUser(login).asLiveData()
}