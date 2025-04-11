package com.aki.notesapp.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aki.notesapp.presentation.addnote.model.Note
import com.aki.notesapp.presentation.addnote.model.NoteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(addNote: List<Note>)

    @Delete
    fun remove(note: Note)

    @Query("select * from note")
    fun getAll(): Flow<List<Note>>

    @Query("select * from note where id =:notesId")
    suspend fun getTaskWithId(notesId: Int): Note
}