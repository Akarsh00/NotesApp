package com.aki.notesapp.presentation.notesscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aki.notesapp.db.dao.NotesDao
import com.aki.notesapp.presentation.addnote.model.AddNotesScreenAction
import com.aki.notesapp.presentation.addnote.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ShowTaskScreenViewModel(private val taskDao: NotesDao) : ViewModel() {

    val addTaskItemList: Flow<List<Note>> =
        taskDao.getAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())



    fun onAction(action: AddNotesScreenAction) {

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


