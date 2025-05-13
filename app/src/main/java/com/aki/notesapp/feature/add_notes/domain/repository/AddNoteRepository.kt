package com.aki.notesapp.feature.add_notes.domain.repository

import com.aki.notesapp.feature.add_notes.domain.model.Note

interface AddNoteRepository {
    suspend fun saveNote(noteDto: Note)

    suspend fun getNoteWithId(noteId: Long): Note
}