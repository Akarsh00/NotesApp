package com.aki.notesapp.presentation.addnote

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aki.notesapp.db.dao.NotesDao
import com.aki.notesapp.presentation.addnote.model.AddNotesScreenAction
import com.aki.notesapp.presentation.addnote.model.AddNotesState
import com.aki.notesapp.presentation.addnote.model.NoteItem
import com.aki.notesapp.presentation.addnote.model.Note
import com.aki.notesapp.presentation.addnote.model.NoteItemType
import com.aki.notesapp.presentation.addnote.model.createNoteItemList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteViewModel(private val noteDao: NotesDao) : ViewModel() {


    private val _addNoteItemList = mutableStateOf(
        AddNotesState(note = Note(listOfNoteItem = createNoteItemList()))
    )
    val addNoteItemList: State<AddNotesState> = _addNoteItemList

    fun loadTaskById(id: Long) {
        viewModelScope.launch {
            val note = noteDao.getTaskWithId(id)
            updateState(note)
        }
    }


    fun updateNoteItem(noteItem: NoteItem, newText: String) {
        val currentNote = _addNoteItemList.value.note
        val updatedItems = currentNote.listOfNoteItem.map {
            if (it == noteItem) it.copy(noteText = newText) else it
        }
        updateState(note = currentNote.copy(listOfNoteItem = updatedItems))
    }


    fun onAction(action: AddNotesScreenAction) {
        when (action) {

            AddNotesScreenAction.OnFabClicked -> {
                updateState(
                    note = addNoteItemList.value.note,
                    isFabIconVisible = true,
                    openBottomSheet = true
                )
            }

            is AddNotesScreenAction.OnNotesTextChange -> {
                updateNoteItem(action.note, action.newNumber)
            }

            AddNotesScreenAction.OnSaveNoteAction -> {
                val listToSave =
                    addNoteItemList.value.note.listOfNoteItem.filter { it.noteText.isNotEmpty() || it.hashTags.isNotEmpty() }

                updateState(
                    note = Note(
                        id = addNoteItemList.value.note.id,
                        listOfNoteItem = listToSave
                    ), snackBarText = "Notes Saved"
                )

                viewModelScope.launch(Dispatchers.IO) {
                    if (addNoteItemList.value.note.listOfNoteItem.isNotEmpty()) {
                        noteDao.add(
                            listOf(addNoteItemList.value.note)
                        )
                    }

                }

            }

            AddNotesScreenAction.OnBottomSheetCloseAction -> {
                updateState(
                    openBottomSheet = false
                )
            }

            is AddNotesScreenAction.OnAddHashTags -> {
                val noteContent = _addNoteItemList.value.note
                val mutableList: MutableList<NoteItem> = noteContent.listOfNoteItem.toMutableList()
                mutableList.add(NoteItem(hashTags = action.hashTags, type = NoteItemType.HASHTAG))
                _addNoteItemList.value.note.listOfNoteItem = mutableList
                updateState(
                    note = _addNoteItemList.value.note,
                    openBottomSheet = false
                )
            }
        }
    }

    private fun updateState(
        note: Note = _addNoteItemList.value.note,
        snackBarText: String = "",
        isLoading: Boolean = false,
        isNewNoteAdded: Boolean = false,
        isFabIconVisible: Boolean = _addNoteItemList.value.isFabIconVisible,
        openBottomSheet: Boolean = false
    ) {
        _addNoteItemList.value = AddNotesState(
            note = note,
            snackBarText = snackBarText,
            isLoading = isLoading,
            isNewNoteAdded = isNewNoteAdded,
            isFabIconVisible = isFabIconVisible,
            showBottomSheet = openBottomSheet,
        )
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
