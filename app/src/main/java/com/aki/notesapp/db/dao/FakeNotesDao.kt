package com.aki.notesapp.db.dao

import com.aki.notesapp.presentation.addnote.model.Note
import com.aki.notesapp.presentation.addnote.model.createNoteItemList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeNoteDao : NotesDao {
    override fun add(addNote: List<Note>) {

    }

    override fun remove(note: Note) {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<Note>> {
        return flowOf(listOf(Note(1, createNoteItemList())))
    }

    override suspend fun getTaskWithId(notesId: Int): Note {
        TODO("Not yet implemented")
    }
}
