package com.aki.notesapp.presentation.addnote

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aki.notesapp.db.dao.NotesDao
import com.aki.notesapp.presentation.addnote.model.AddNotesScreenAction
import com.aki.notesapp.presentation.addnote.model.AddNotesState
import com.aki.notesapp.presentation.addnote.model.NoteItem
import com.aki.notesapp.presentation.addnote.model.Note
import com.aki.notesapp.presentation.addnote.model.createNoteItemList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteViewModel(private val noteDao: NotesDao) : ViewModel() {


    val addNoteItemList = mutableStateOf(
        AddNotesState(
            note = Note(lisOfNoteItem = createNoteItemList()),
            isLoading = false,
            isNewNoteAdded = false
        )
    )

    fun loadTaskById(id: Int) {
        viewModelScope.launch {
            val note = noteDao.getTaskWithId(id)
            addNoteItemList.value = AddNotesState(
                note = note,
                snackBarText = "",
                isLoading = false,
                isNewNoteAdded = false
            )
        }
    }

    fun updateNoteItem(noteItem: NoteItem, newText: String) {
        val currentList = addNoteItemList.value.note.lisOfNoteItem.toMutableList()
        val index = currentList.indexOf(noteItem)

        if (index != -1) {
            currentList[index] = noteItem.copy(noteText = newText)
            addNoteItemList.value = AddNotesState(
                note = Note(
                    id = addNoteItemList.value.note.id,
                    lisOfNoteItem = currentList
                ),
                snackBarText = "",
                isLoading = false,
                isNewNoteAdded = false
            )

        }
    }

    fun onAction(action: AddNotesScreenAction) {
        when (action) {

            AddNotesScreenAction.OnFabClicked -> {}

            is AddNotesScreenAction.OnNotesTextChange -> {
                updateNoteItem(action.note, action.newNumber)
            }

            AddNotesScreenAction.OnSaveNoteAction -> {
                val listToSave =
                    addNoteItemList.value.note.lisOfNoteItem.filter { it.noteText.isNotEmpty() }
                addNoteItemList.value = AddNotesState(
                    note = Note(
                        id = addNoteItemList.value.note.id,
                        lisOfNoteItem = listToSave
                    ),
                    snackBarText = "Notes Saved",
                    isLoading = false,
                    isNewNoteAdded = false
                )

                viewModelScope.launch(Dispatchers.IO) {
                    noteDao.add(
                        listOf(addNoteItemList.value.note)
                    )
                }

            }
        }
    }
}


class AddNoteViewModelFactory(
    private val noteDao: NotesDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return AddNoteViewModel(noteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
