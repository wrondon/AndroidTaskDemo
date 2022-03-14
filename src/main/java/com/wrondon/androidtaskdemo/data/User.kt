package com.wrondon.androidtaskdemo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject

@Entity
data class User(@PrimaryKey val id: Int, val email: String, val first_name: String, val last_name: String, val avatar: String)

@Entity
data class Support(val url: String, val text: String)

data class MyResponse(val page: Int, val per_page: Int, val data : List<User>, val support: Support)

data class LoginResponse(val token: String, val error: String)
