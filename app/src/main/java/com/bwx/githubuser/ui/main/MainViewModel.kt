package com.bwx.githubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bwx.githubuser.api.ApiConfig
import com.bwx.githubuser.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        var TAG = "main_view_model"
    }

    private val _listUsers = MutableLiveData<List<UserRespons>>()
    val listUsers: LiveData<List<UserRespons>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingFragment = MutableLiveData<Boolean>()
    val isLoadingFragment: LiveData<Boolean> = _isLoadingFragment


    init {
        getListUsers()
    }

    private fun getListUsers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUsers()
        client.enqueue(object : Callback<List<UserRespons>> {
            override fun onResponse(
                call: Call<List<UserRespons>>,
                response: Response<List<UserRespons>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUsers.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<UserRespons>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun searchUser(q: String): LiveData<SearchResponse> {
        _isLoading.value = true
        val searchResponse = MutableLiveData<SearchResponse>()
        val client = ApiConfig.getApiService().searchUser(q)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    searchResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })

        return searchResponse
    }

    fun getFollower(login: String): LiveData<List<FollowerResponse>> {
        _isLoadingFragment.value = true
        val listFollower = MutableLiveData<List<FollowerResponse>>()
        val client = ApiConfig.getApiService().getFollower(login)
        client.enqueue(object : Callback<List<FollowerResponse>> {
            override fun onResponse(
                call: Call<List<FollowerResponse>>,
                response: Response<List<FollowerResponse>>
            ) {
                _isLoadingFragment.value = false
                if (response.isSuccessful) {
                    listFollower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: test")
                }
            }

            override fun onFailure(call: Call<List<FollowerResponse>>, t: Throwable) {
                _isLoadingFragment.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })

        return listFollower
    }

    fun getFollowing(login: String): LiveData<List<FollowingResponse>> {
        _isLoadingFragment.value = true
        val listFollowing = MutableLiveData<List<FollowingResponse>>()
        val client = ApiConfig.getApiService().getFollowing(login)
        client.enqueue(object : Callback<List<FollowingResponse>> {
            override fun onResponse(
                call: Call<List<FollowingResponse>>,
                response: Response<List<FollowingResponse>>
            ) {
                _isLoadingFragment.value = false
                if (response.isSuccessful) {
                    listFollowing.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<FollowingResponse>>, t: Throwable) {
                _isLoadingFragment.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
        return listFollowing
    }

}