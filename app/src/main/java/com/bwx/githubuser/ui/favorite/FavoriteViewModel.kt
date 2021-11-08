package com.bwx.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bwx.githubuser.data.GithubRepository
import com.bwx.githubuser.data.local.entity.UserEntity

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mGithubRepository: GithubRepository = GithubRepository(application)

    fun getListFavoriteUser(): LiveData<List<UserEntity>> = mGithubRepository.getListFavoriteUser()

    fun deleteFavoriteUser(user: UserEntity) = mGithubRepository.deleteFavoriteUser(user)

}