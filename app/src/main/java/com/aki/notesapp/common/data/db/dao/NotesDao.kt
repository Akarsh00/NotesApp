package com.aki.notesapp.common.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aki.notesapp.common.data.dto.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(addNoteEntity: List<NoteEntity>)


    @Delete
    fun remove(noteEntity: NoteEntity)

    @Query("select * from note")
    fun getAll(): Flow<List<NoteEntity>>

    @Query("select * from note where id =:notesId")
    suspend fun getNoteWithId(notesId: Long): NoteEntity
}