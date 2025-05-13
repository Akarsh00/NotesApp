package com.aki.notesapp.feature.show_notes.domain

import com.aki.notesapp.feature.add_notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesScreenRepository {

    suspend fun getAllNotes(): Flow<List<Note>>
}