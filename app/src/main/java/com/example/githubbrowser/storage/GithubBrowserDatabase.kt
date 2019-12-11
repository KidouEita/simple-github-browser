package com.example.githubbrowser.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubbrowser.model.LoginData
import com.example.githubbrowser.storage.dao.LoginDataDao

@Database(
    entities = [LoginData::class],
    version = 1
)
abstract class GithubBrowserDatabase : RoomDatabase() {

    companion object {
        var instance: GithubBrowserDatabase? = null
        fun buildDatabase(context: Context) {
            instance ?: run {
                synchronized(GithubBrowserDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GithubBrowserDatabase::class.java, "sgb.db"
                    ).build()
                }
            }
        }
    }

    abstract fun loginDataDao(): LoginDataDao
}