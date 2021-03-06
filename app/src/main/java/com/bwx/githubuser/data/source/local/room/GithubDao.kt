package com.bwx.githubuser.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import com.bwx.githubuser.data.source.local.entity.FollowersEntity
import com.bwx.githubuser.data.source.local.entity.FollowingEntity
import com.bwx.githubuser.data.source.local.entity.RepositoryEntity
import com.bwx.githubuser.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRepositories(repositories: List<RepositoryEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFollowing(following: List<FollowingEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFollowers(followers: List<FollowersEntity>)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT id FROM user ORDER BY id DESC LIMIT 1")
    suspend fun getRemoteKey(): Int

    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getPagingUsers(): PagingSource<Int, UserEntity>

    @Query("SELECT * FROM user WHERE login=:login")
    fun getUser(login: String): Flow<UserEntity>

    @Query("SELECT * FROM repository WHERE login=:login")
    fun getRepository(login: String): Flow<List<RepositoryEntity>>

    @Query("SELECT * FROM following WHERE login_following=:login")
    fun getUserFollowing(login: String): Flow<List<FollowingEntity>>

    @Query("SELECT * FROM followers WHERE login_followers=:login")
    fun getUserFollowers(login: String): Flow<List<FollowersEntity>>

}