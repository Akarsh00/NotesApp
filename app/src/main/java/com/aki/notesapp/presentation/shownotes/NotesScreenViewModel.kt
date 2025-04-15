package com.aki.notesapp.presentation.shownotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aki.notesapp.db.dao.NotesDao
import com.aki.notesapp.db.model.Note
import com.aki.notesapp.presentation.shownotes.action.NotesScreenAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShowNoteScreenViewModel(private val taskDao: NotesDao) : ViewModel() {

    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList: StateFlow<List<Note>> = _noteList

    init {
        viewModelScope.launch {
            taskDao.getAll().collect { notes ->
                _noteList.value = notes
            }
        }
    }

    fun onAction(action: NotesScreenAction) {
        when (action) {

            is NotesScreenAction.NotesScreenExpandCollapseClicked -> {
                val updatedList = _noteList.value.map { note ->
                    if (note.id == action.noteId) {
                        val updated = note.copy(expanded = !note.expanded)
                        updated
                    } else {
                        note
                    }
                }
                _noteList.value = updatedList

            }
        }
    }
}

class ShowNotesScreenViewModelFactory(
    private val noteDao: NotesDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShowNoteScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShowNoteScreenViewModel(noteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}


