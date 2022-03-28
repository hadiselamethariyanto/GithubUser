package com.bwx.githubuser.di

import androidx.room.Room
import com.bwx.githubuser.BuildConfig
import com.bwx.githubuser.data.repository.GithubRepository
import com.bwx.githubuser.data.repository.PageKeyedRemoteMediator
import com.bwx.githubuser.data.source.local.LocalDataSource
import com.bwx.githubuser.data.source.local.room.GithubDatabase
import com.bwx.githubuser.data.source.remote.RemoteDataSource
import com.bwx.githubuser.data.source.remote.network.ApiService
import com.bwx.githubuser.domain.repository.IGithubRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val databaseModule = module {
    factory { get<GithubDatabase>().githubDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            GithubDatabase::class.java, "github.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { PageKeyedRemoteMediator(get(), get()) }
    single<IGithubRepository> { GithubRepository(get(), get()) }
}

val networkModule = module {
    single {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}