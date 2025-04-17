package com.aki.notesapp.di

import android.content.Context
import androidx.room.Room
import com.aki.notesapp.db.AppDatabase
import com.aki.notesapp.db.dao.NotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context = context, AppDatabase::class.java, "note_db").build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(database: AppDatabase): NotesDao {
        return database.notesDao()
    }
}