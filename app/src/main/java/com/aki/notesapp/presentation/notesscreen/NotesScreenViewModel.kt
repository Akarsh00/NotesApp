package com.aki.notesapp.presentation.notesscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aki.notesapp.db.dao.NotesDao
import com.aki.notesapp.presentation.addnote.model.Note
import com.aki.notesapp.presentation.notesscreen.model.NotesScreenAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShowTaskScreenViewModel(private val taskDao: NotesDao) : ViewModel() {

    private val _taskList = MutableStateFlow<List<Note>>(emptyList())
    val taskList: StateFlow<List<Note>> = _taskList

    init {
        viewModelScope.launch {
            taskDao.getAll().collect { notes ->
                _taskList.value = notes
            }
        }
    }

    fun onAction(action: NotesScreenAction) {
        when (action) {

            is NotesScreenAction.NotesScreenExpandCollapseClicked -> {
                val updatedList = _taskList.value.map { note ->
                    if (note.id == action.noteId) {
                        val updated = note.copy(expanded = !note.expanded)
                        updated
                    } else {
                        note
                    }
                }
                _taskList.value = updatedList

            }
        }
    }
}

class ShowNotesScreenViewModelFactory(
    private val noteDao: NotesDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShowTaskScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShowTaskScreenViewModel(noteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}


