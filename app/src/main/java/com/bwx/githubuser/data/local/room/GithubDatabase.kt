package com.bwx.githubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bwx.githubuser.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao

    companion object {
        @Volatile
        private var INSTANCE: GithubDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GithubDatabase {
            if (INSTANCE == null) {
                synchronized(GithubDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GithubDatabase::class.java, "note_database"
                    )
                        .build()
                }
            }
            return INSTANCE as GithubDatabase
        }
    }
}

