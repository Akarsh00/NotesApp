package com.aki.notesapp.feature.show_notes.data.impl

import com.aki.notesapp.common.data.db.dao.NotesDao
import com.aki.notesapp.common.data.dto.toDomain
import com.aki.notesapp.feature.add_notes.domain.model.Note
import com.aki.notesapp.feature.show_notes.domain.NotesScreenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class NotesScreenRepositoryImpl @Inject constructor(val dao: NotesDao) : NotesScreenRepository {
    override suspend fun getAllNotes(): Flow<List<Note>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

}