package com.wrondon.androidtaskdemo.api


import com.wrondon.androidtaskdemo.data.LoginResponse
import com.wrondon.androidtaskdemo.data.MyResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ReqResService {

    @GET("api/users")
    suspend fun users(): Response<MyResponse>

    @FormUrlEncoded
    @POST("api/login")
    suspend fun login( @Field("email") email: String, @Field("password") password :String): Response<LoginResponse>

    companion object {
        const val BASE_URL = "https://reqres.in/"
        fun create(): ReqResService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ReqResService::class.java)
        }
    }

}

fun main() {

    runBlocking {
        launch(){
            val resp = ReqResService.create().users()
           if(resp.isSuccessful){
               println(" ${resp.body()?.data}")
           }
        }
    }
    println("End...")
}