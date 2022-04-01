package com.bwx.githubuser.ui.followers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.githubuser.domain.usecase.GithubUseCase

class FollowersViewModel(private val repository: GithubUseCase) : ViewModel() {

    fun getUserFollowers(login: String) = repository.getUserFollowers(login).asLiveData()
}