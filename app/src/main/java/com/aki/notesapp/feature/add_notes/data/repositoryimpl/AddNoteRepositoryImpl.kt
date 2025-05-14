package com.aki.notesapp.feature.add_notes.data.repositoryimpl

import com.aki.notesapp.common.data.db.dao.NotesDao
import com.aki.notesapp.feature.add_notes.domain.model.Note
import com.aki.notesapp.common.data.dto.toDomain
import com.aki.notesapp.common.data.dto.toEntity
import com.aki.notesapp.feature.add_notes.domain.repository.AddNoteRepository
import javax.inject.Inject

class AddNoteRepositoryImpl @Inject constructor(val notesDao: NotesDao) : AddNoteRepository {


    override suspend fun saveNote(note: Note) {
        notesDao.add(listOf(note.toEntity()))
    }

    override suspend fun getNoteWithId(noteId: Long) =
        notesDao.getNoteWithId(notesId = noteId).toDomain()
}