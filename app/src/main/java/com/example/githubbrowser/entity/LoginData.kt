package com.example.githubbrowser.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_data")
data class LoginData(

    @PrimaryKey val id: Int,
    val name: String,
    val avatarUrl: String,
    val token: String
)