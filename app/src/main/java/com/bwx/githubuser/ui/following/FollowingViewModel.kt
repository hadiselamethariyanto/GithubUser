package com.bwx.githubuser.ui.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.githubuser.domain.usecase.GithubUseCase

class FollowingViewModel(private val repository: GithubUseCase) : ViewModel() {

    fun getUserFollowing(login: String) = repository.getUserFollowing(login).asLiveData()

}