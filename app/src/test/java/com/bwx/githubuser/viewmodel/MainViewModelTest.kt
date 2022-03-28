package com.bwx.githubuser.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bwx.githubuser.MainCoroutineScopeRule
import com.bwx.githubuser.data.Resource
import com.bwx.githubuser.domain.model.User
import com.bwx.githubuser.domain.usecase.GithubUseCase
import com.bwx.githubuser.ui.main.MainViewModel
import com.bwx.githubuser.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*

class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private val dummyUser = DataDummy.generateUserDummy()

    @Mock
    private lateinit var mockObserver: Observer<Resource<User>>

    private lateinit var myViewModel: MainViewModel

    @Mock
    private lateinit var useCase: GithubUseCase

    @Captor
    private lateinit var captor: ArgumentCaptor<Resource<User>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        myViewModel = MainViewModel(useCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getDetailUser() {
        coroutineScope.runBlockingTest {
            val response = Resource.Success(dummyUser)
            val channel = Channel<Resource<User>>()
            val flow = channel.consumeAsFlow()
            Mockito.`when`(useCase.getDetailUser(dummyUser.login)).thenReturn(flow)

            launch {
                channel.send(response)
            }

            val liveData = myViewModel.getDetailUser(dummyUser.login)
            liveData.observeForever(mockObserver)

            verify(mockObserver).onChanged(captor.capture())
            Assert.assertEquals(dummyUser.name, captor.value.data?.name)
            Assert.assertEquals(dummyUser.email, captor.value.data?.email)
            Assert.assertEquals(dummyUser.id, captor.value.data?.id)
            Assert.assertEquals(dummyUser.login, captor.value.data?.login)
            Assert.assertEquals(dummyUser.avatar_url, captor.value.data?.avatar_url)
            Assert.assertEquals(dummyUser.repos_url, captor.value.data?.repos_url)
            Assert.assertEquals(dummyUser.created_at, captor.value.data?.created_at)
        }
    }

}