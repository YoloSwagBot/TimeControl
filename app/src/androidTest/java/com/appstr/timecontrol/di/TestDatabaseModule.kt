package com.appstr.timecontrol.di

import android.content.Context
import androidx.room.Room
import com.appstr.timecontrol.data.GameStateDatabase
import com.appstr.timecontrol.data.daos.GameStateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TestDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appli: Context): GameStateDatabase {
        return Room.inMemoryDatabaseBuilder(appli, GameStateDatabase::class.java)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideGameDao(database: GameStateDatabase): GameStateDao = database.gameDao()

}


