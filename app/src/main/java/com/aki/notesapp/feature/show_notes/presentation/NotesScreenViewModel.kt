package com.aki.notesapp.feature.show_notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aki.notesapp.common.data.db.dao.NotesDao
import com.aki.notesapp.common.data.dto.NoteEntity
import com.aki.notesapp.feature.add_notes.domain.model.Note
import com.aki.notesapp.feature.show_notes.data.impl.NotesScreenRepositoryImpl
import com.aki.notesapp.feature.show_notes.domain.NotesScreenRepository
import com.aki.notesapp.feature.show_notes.presentation.action.NotesScreenViewModelAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowNoteScreenViewModel @Inject constructor(private val repository : NotesScreenRepository) :
    ViewModel() {

    private val _noteEntityList = MutableStateFlow<List<Note>>(emptyList())
    val noteEntityList: StateFlow<List<Note>> = _noteEntityList

    init {
        viewModelScope.launch {
            repository.getAllNotes().collect { notes ->
                _noteEntityList.value = notes
            }
        }
    }

    fun onAction(action: NotesScreenViewModelAction) {
        when (action) {

            is NotesScreenViewModelAction.NotesScreenExpandCollapseClicked -> {
                val updatedList = _noteEntityList.value.map { note ->
                    if (note.id == action.noteId) {
                        val updated = note.copy(expanded = !note.expanded)
                        updated
                    } else {
                        note
                    }
                }
                _noteEntityList.value = updatedList

            }
        }
    }
}



