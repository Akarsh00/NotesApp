package com.aki.notesapp.feature.add_notes.domain.model

import com.aki.notesapp.common.domain.model.NoteItem

data class Note(
    val id: Long = 0L,
    var listOfNoteItem: List<NoteItem>,
    val isTaskCompleted: Boolean = false,
    var expanded: Boolean = false
)



