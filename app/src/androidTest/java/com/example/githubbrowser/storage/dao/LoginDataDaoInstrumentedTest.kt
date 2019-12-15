package com.example.githubbrowser.storage.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubbrowser.model.LoginData
import com.example.githubbrowser.storage.GithubBrowserDatabase
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LoginDataDaoInstrumentedTest {
    private lateinit var userDataDao: LoginDataDao
    private lateinit var db: GithubBrowserDatabase

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GithubBrowserDatabase::class.java).build()
        userDataDao = db.loginDataDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() { db.close() }

    @Test
    @Throws(Exception::class)
    fun writeDataAndReadInList() {
        val data = LoginData(0, "TestName", "AvatarUrl", "token")
        userDataDao.insertLoginData(data)
        val dataRead = userDataDao.getLoggedInData()
        assertThat(dataRead, equalTo(data))
    }
}