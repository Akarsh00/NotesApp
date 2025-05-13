package com.aki.notesapp.common.data.dto

import com.aki.notesapp.feature.add_notes.domain.model.Note

fun NoteEntity.toDomain(): Note = Note(
    id = id,
    listOfNoteItem = listOfNoteItem,
    isTaskCompleted = isTaskCompleted,
    expanded = expanded
)

fun Note.toEntity(): NoteEntity = NoteEntity(
    id = id,
    listOfNoteItem = listOfNoteItem,
    isTaskCompleted = isTaskCompleted,
    expanded = expanded
)
