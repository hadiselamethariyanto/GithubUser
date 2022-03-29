package com.bwx.githubuser.source.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bwx.githubuser.data.source.remote.network.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class ApiServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getPagingUsers() {
        enqueueResponse("list-users.json")

        val users = runBlocking {
            service.getPagingUsers(10, 0)
        }
        val request = mockWebServer.takeRequest()

        Assert.assertEquals(request.path,"/users?per_page=10&since=0")
        Assert.assertEquals(users.size, 10)
        Assert.assertEquals(users[0].login,"mojombo")
        Assert.assertEquals(users[0].id,1)
        Assert.assertEquals(users[0].reposUrl,"https://api.github.com/users/mojombo/repos")
        Assert.assertEquals(users[0].avatarUrl,"https://avatars.githubusercontent.com/u/1?v=4")
        Assert.assertEquals(users[0].organizationsUrl,"https://api.github.com/users/mojombo/orgs")
        Assert.assertEquals(users[0].followersUrl,"https://api.github.com/users/mojombo/followers")
        Assert.assertEquals(users[0].followingUrl,"https://api.github.com/users/mojombo/following{/other_user}")
        Assert.assertEquals(users[0].starredUrl,"https://api.github.com/users/mojombo/starred{/owner}{/repo}")
    }

    @Test
    fun getDetailUsers(){
        enqueueResponse("detail-user.json")

        val user = runBlocking {
            service.getDetailUsers("mojombo")
        }

        val request = mockWebServer.takeRequest()

        Assert.assertNotNull(user)
        Assert.assertEquals(request.path,"/users/mojombo")
        Assert.assertEquals(user.name,"Tom Preston-Werner")
        Assert.assertEquals(user.avatarUrl,"https://avatars.githubusercontent.com/u/1?v=4")
        Assert.assertEquals(user.company,"@chatterbugapp, @redwoodjs, @preston-werner-ventures ")
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")

        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}