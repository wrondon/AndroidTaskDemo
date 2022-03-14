package com.wrondon.androidtaskdemo.data

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String
)

data class InvalidUser(val userId: String,
                       val message: String)