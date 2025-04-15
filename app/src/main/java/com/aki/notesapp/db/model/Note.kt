package com.aki.notesapp.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var listOfNoteItem: List<NoteItem>,
    val isTaskCompleted: Boolean = false,
    var expanded: Boolean = false

)



