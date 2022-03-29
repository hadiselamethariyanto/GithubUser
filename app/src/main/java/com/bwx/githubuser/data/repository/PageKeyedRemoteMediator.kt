package com.bwx.githubuser.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bwx.githubuser.data.source.local.LocalDataSource
import com.bwx.githubuser.data.source.local.entity.UserEntity
import com.bwx.githubuser.data.source.remote.RemoteDataSource
import com.bwx.githubuser.utils.DataMapper
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = localDataSource.getRemoteKey()
                    remoteKey
                }

            }
            val items = remoteDataSource.getPagingUsers(per_page = 10, since = loadKey)

            val userEntities = DataMapper.mapUsersResponseToEntities(items)
            localDataSource.insertUsers(userEntities)
            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}