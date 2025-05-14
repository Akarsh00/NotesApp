package com.aki.notesapp.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aki.notesapp.common.data.db.dao.NotesDao
import com.aki.notesapp.common.data.db.dao.converter.NoteItemListConverter
import com.aki.notesapp.common.data.dto.NoteEntity

@Database(entities = [NoteEntity::class], version = 1,exportSchema = true)
@TypeConverters(NoteItemListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}

