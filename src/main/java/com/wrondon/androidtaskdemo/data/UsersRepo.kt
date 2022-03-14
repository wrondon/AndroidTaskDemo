package com.wrondon.androidtaskdemo.data

import android.util.Log
import com.wrondon.androidtaskdemo.api.ReqResService
import com.wrondon.androidtaskdemo.data.MyResult
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class UsersRepo @Inject constructor(private val service: ReqResService) {

    suspend fun getUserList(): List<User> {
        var result = listOf<User>()
        coroutineScope {
            withContext(Dispatchers.IO){
                val users: Response<MyResponse> = service.users()
                if (users.isSuccessful){
                    result = users.body()?.data!!
                }
            }
        }
        return result
    }

    suspend fun login(username: String, password: String): String {
        var result = ""
        coroutineScope {
            withContext(Dispatchers.IO) {
                try {
                    val loginResponse = service.login(username, password)
                    if(loginResponse.isSuccessful) {
                        val respUser = LoggedInUser(java.util.UUID.randomUUID().toString(), " $username (display name)")
                        result = " Authenticated : \n  ${loginResponse.body()}"
                    } else {
                        val message = " WRONG! Identification :\n ${loginResponse.errorBody()?.string()}"
                        result = message
                    }
                } catch (e: Throwable) {
                    Log.d("empty-comp2"," WRONG - EXCEPTION - AUTHENTICATION!!!  exception(THROWABLE): $e")
                }
            }
        }
        return result

    }
}

fun main() {
    runBlocking {
        launch(){
            val r = UsersRepo(ReqResService.create())
            println("@ REPO using its getUserlist (here)    : ${r.getUserList()}")
            println("--------------------------------------------------------")
            println("@ REPO using its LOGIN (here) ...      : ${r.login("eve.holt@reqres.in","cityslicka")}")
        }
    }

    val r = UsersRepo(ReqResService.create())
    println(" !! End !!")
}