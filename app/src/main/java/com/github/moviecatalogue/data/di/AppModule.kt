package com.github.moviecatalogue.data.di

import android.content.Context
import com.github.moviecatalogue.BuildConfig
import com.github.moviecatalogue.MainApplication
import com.github.moviecatalogue.data.network.ApiServices
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class AppModule {
    private val connectionTimeout: Long = 50
    private val readTimeout: Long = 50
    private val writeTimeout: Long = 50

    @Provides
    fun provideContext(application: MainApplication): Context{
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideRequestService(): ApiServices{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(ApiServices::class.java)
    }
}