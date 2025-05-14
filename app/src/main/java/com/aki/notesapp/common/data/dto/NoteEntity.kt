package com.aki.notesapp.common.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aki.notesapp.common.domain.model.NoteItem

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var listOfNoteItem: List<NoteItem>,
    val isTaskCompleted: Boolean = false,
    var expanded: Boolean = false
)



