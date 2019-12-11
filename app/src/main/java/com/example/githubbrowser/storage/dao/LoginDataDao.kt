package com.example.githubbrowser.storage.dao

import androidx.room.*
import com.example.githubbrowser.entity.LoginData

@Dao
interface LoginDataDao {

    @Query("SELECT * FROM login_data LIMIT 1")
    fun getLoggedInData(): LoginData?

    @Query("DELETE FROM login_data")
    fun cleanLoginData()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLoginData(loginData: LoginData)

    @Update
    fun updateLoginData(loginData: LoginData)

    @Delete
    fun deleteLoginData(loginData: LoginData)
}