package com.aki.notesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aki.notesapp.db.dao.NotesDao
import com.aki.notesapp.db.dao.converter.NoteItemListConverter
import com.aki.notesapp.presentation.addnote.model.Note

@Database(entities = [Note::class], version = 1)
@TypeConverters(NoteItemListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}


object NoteDatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "note_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
