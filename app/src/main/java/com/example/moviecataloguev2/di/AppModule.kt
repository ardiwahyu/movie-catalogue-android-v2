package com.example.moviecataloguev2.di

import android.content.Context
import com.example.moviecataloguev2.BuildConfig.BASE_URL
import com.example.moviecataloguev2.BuildConfig.DEBUG
import com.example.moviecataloguev2.data.remote.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    private const val CONNECT_TIMEOUT: Long = 30
    private const val READ_TIMEOUT: Long = 30
    private const val WRITE_TIMEOUT: Long = 30

    @Provides
    @Singleton
    fun provideApiService(@ApplicationContext _context: Context): ApiServices {
        val apiServices: ApiServices
        val client = OkHttpClient.Builder()
        if(DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(interceptor)
        }
        client
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .cache(null)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

        apiServices = retrofit.create(ApiServices::class.java)
        return apiServices
    }
}