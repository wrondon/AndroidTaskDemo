package com.wrondon.androidtaskdemo.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wrondon.androidtaskdemo.api.ReqResService
import com.wrondon.androidtaskdemo.data.LoggedInUser
import com.wrondon.androidtaskdemo.data.User
import com.wrondon.androidtaskdemo.data.UsersRepo

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UsersRepo
) : ViewModel() {

    var userslistResponse: List<User>  by mutableStateOf(listOf<User>())
    var errorMessage: String by mutableStateOf("")
    var loginMessage: String by mutableStateOf("")

   fun getUsers() {
       var result = listOf<User>()
       viewModelScope.launch {
           try {
               result =  repository.getUserList()
               Log.d("empty-comp"," result (list-users:) $result")
               userslistResponse = result
           }
           catch (e: Exception) {
               errorMessage = e.message.toString()
           }
       }
    }

    fun login(username: String, password : String) {
        var result = ""
        viewModelScope.launch {
            try {
                result = repository.login(username = username, password = password)
                Log.d("empty-comp"," result ((login:)) $result")
                loginMessage =  result
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

}


