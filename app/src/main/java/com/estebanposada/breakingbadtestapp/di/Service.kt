package com.estebanposada.breakingbadtestapp.di

import com.estebanposada.breakingbadtestapp.data.server.BreakingBadApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object Service {

    @Provides
    @Singleton
    fun provideBreakingBadService(client: OkHttpClient): BreakingBadApi = Retrofit.Builder()
        .baseUrl("https://www.breakingbadapi.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BreakingBadApi::class.java)

    @Provides
    fun provideClient(): OkHttpClient =
        HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(this).build()
        }
}