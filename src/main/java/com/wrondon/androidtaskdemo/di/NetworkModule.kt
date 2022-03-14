package com.wrondon.androidtaskdemo.di



import com.wrondon.androidtaskdemo.api.ReqResService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Call
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule  {
    @Singleton
    @Provides
    fun provideReqResService(): ReqResService {
        return ReqResService.create()
    }


}

