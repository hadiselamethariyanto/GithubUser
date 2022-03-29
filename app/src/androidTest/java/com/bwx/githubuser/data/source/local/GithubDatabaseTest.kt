package com.bwx.githubuser.data.source.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.bwx.githubuser.data.source.local.entity.UserEntity
import com.bwx.githubuser.data.source.local.room.GithubDao
import com.bwx.githubuser.data.source.local.room.GithubDatabase
import com.bwx.githubuser.utils.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class GithubDatabaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var githubDao: GithubDao
    private lateinit var db: GithubDatabase
    private val dummyUsers = DataDummy.generateUsersEntityDummy()


    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, GithubDatabase::class.java
        ).allowMainThreadQueries().build()
        githubDao = db.githubDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    fun insertUser() = runBlocking {
        githubDao.insertUsers(dummyUsers)
        val users = githubDao.getUser("selamet").take(1).toList()
        val user = users[0]
        assertEquals(2, user.id)
        assertEquals("selamet", user.login)
        assertEquals("", user.repos_url)
        assertEquals("", user.avatar_url)
        assertEquals("", user.company)
        assertEquals("Banyuwangi", user.location)
        assertEquals("selamet", user.name)
        assertEquals("selamet@gmail.com", user.email)
        assertEquals("", user.created_at)
        assertEquals(false, user.isFav)
    }

    @Test
    fun updateUser() = runBlocking {
        githubDao.insertUsers(dummyUsers)

        val user = UserEntity(
            id = 1,
            login = "m.hadi.s.h",
            repos_url = "",
            avatar_url = "",
            company = "google",
            location = "jakarta",
            email = "muhammad.hadi.selamet@gmail.com",
            name = "mas hadi",
            created_at = "29/03/2022",
            isFav = true
        )

        githubDao.updateUser(user)

        val mUser = githubDao.getUser(user.login).take(1).toList()
        assertEquals(user, mUser[0])
    }

    @Test
    fun getRemoteKey() = runBlocking {
        githubDao.insertUsers(dummyUsers)
        val key = githubDao.getRemoteKey()

        assertEquals(2, key)
    }


}