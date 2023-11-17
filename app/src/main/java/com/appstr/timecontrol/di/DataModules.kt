package com.appstr.timecontrol.di

import android.content.Context
import androidx.room.Room
import com.appstr.timecontrol.domain.repo.GameDao
import com.appstr.timecontrol.domain.repo.GameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GameDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GameDatabase::class.java,
            GameDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideGameDao(database: GameDatabase): GameDao = database.gameDao()

}


