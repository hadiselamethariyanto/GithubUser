package com.bwx.githubuser.ui.detail_user

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bwx.githubuser.api.ApiConfig
import com.bwx.githubuser.data.GithubRepository
import com.bwx.githubuser.data.local.entity.UserEntity
import com.bwx.githubuser.model.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    companion object {
        var TAG = "detail_view_model"
    }

    private val mGithubRepository: GithubRepository = GithubRepository(application)


    private val _userEntity = MutableLiveData<UserEntity>()
    private val userEntity: LiveData<UserEntity> = _userEntity

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailUser(login: String): LiveData<DetailUserResponse> {
        _isLoading.value = true
        val user = MutableLiveData<DetailUserResponse>()
        val client = ApiConfig.getApiService().getDetailUsers(login)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    user.value = response.body()
                    _userEntity.value = UserEntity(
                        id = response.body()?.id,
                        login = response.body()?.login,
                        repos_url = response.body()?.reposUrl,
                        avatar_url = response.body()?.avatarUrl,
                        company = response.body()?.company,
                        location = response.body()?.location,
                        name = response.body()?.name,
                        isFav = false
                    )
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })

        return user
    }

    fun setFavoriteUser() = userEntity.value?.let { mGithubRepository.getFavoriteUser(it) }

}