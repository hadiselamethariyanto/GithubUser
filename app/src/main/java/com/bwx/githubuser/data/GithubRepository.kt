package com.bwx.githubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.bwx.githubuser.data.local.entity.UserEntity
import com.bwx.githubuser.data.local.room.GithubDao
import com.bwx.githubuser.data.local.room.GithubDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GithubRepository(application: Application) {
    private val mGithubDao: GithubDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = GithubDatabase.getDatabase(application)
        mGithubDao = db.githubDao()
    }

    fun getFavoriteUser(user: UserEntity) {
        executorService.execute {
            if (mGithubDao.getFavoriteUser(user.id!!)){
                mGithubDao.delete(user)
            }else{
                mGithubDao.insert(user)
            }
        }
    }

    fun deleteFavoriteUser(user: UserEntity){
        executorService.execute{
            mGithubDao.delete(user)
        }
    }

    fun getListFavoriteUser(): LiveData<List<UserEntity>> = mGithubDao.getListFavoriteUser()
}