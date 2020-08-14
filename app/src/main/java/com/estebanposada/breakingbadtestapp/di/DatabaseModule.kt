package com.estebanposada.breakingbadtestapp.di

import android.content.Context
import androidx.room.Room
import com.estebanposada.breakingbadtestapp.data.database.AppDatabase
import com.estebanposada.breakingbadtestapp.data.database.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "bb_characters-db"
        ).build()

    @Provides
    fun provideCharacterDao(db: AppDatabase): CharacterDao = db.characterDao()
}