package com.bwx.githubuser.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.bwx.githubuser.data.source.local.LocalDataSource
import com.bwx.githubuser.data.source.remote.RemoteDataSource
import com.bwx.githubuser.utils.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


class FakeGithubRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)

    private val githubRepository = FakeGithubRepository(remote, local)
    private val dummyUser = DataDummy.generateUserEntityDummy()

    @Test
    fun getDetailUser() {
        runBlocking {
            val flow = flow {
                emit(dummyUser)
            }

            `when`(local.getUser("hadi")).thenReturn(flow)
            val test = githubRepository.getDetailUser("hadi")

            test.test {
                assertEquals(
                    DataMapper.mapUserEntityToDomain(dummyUser),
                    expectMostRecentItem().data
                )
            }
        }
    }

}