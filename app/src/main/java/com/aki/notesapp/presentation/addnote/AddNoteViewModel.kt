package com.aki.notesapp.presentation.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aki.notesapp.common.createNoteItemList
import com.aki.notesapp.db.dao.NotesDao
import com.aki.notesapp.presentation.addnote.action.AddNotesScreenAction
import com.aki.notesapp.presentation.addnote.state.AddNotesState
import com.aki.notesapp.db.model.NoteItem
import com.aki.notesapp.db.model.Note
import com.aki.notesapp.db.model.NoteItemType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddNoteViewModel(private val noteDao: NotesDao) : ViewModel() {


    private val _addNoteItemList = MutableStateFlow(
        AddNotesState(note = Note(listOfNoteItem = createNoteItemList()))
    )
    val addNoteItemList = _addNoteItemList.asStateFlow()

    fun loadNoteById(id: Long) {
        viewModelScope.launch {
            val note = noteDao.getNoteWithId(id)
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
//                updateState(
//                    note = addNoteItemList.value.note,
//                    isFabIconVisible = false,
//                    openBottomSheet = true
//                )
            }

            is AddNotesScreenAction.OnNotesTextChange -> {
                updateNoteItem(action.note, action.newNumber)
            }

            AddNotesScreenAction.OnSaveNoteAction -> {
                val listToSave =
                    addNoteItemList.value.note.listOfNoteItem.filter { it.noteText.isNotEmpty() || it.hashTags.isNotEmpty() || it.noteAttachments.isNotEmpty() }

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
                val currentNote = _addNoteItemList.value.note
                val updatedList = currentNote.listOfNoteItem.toMutableList().apply {
                    add(NoteItem(hashTags = action.hashTags, type = NoteItemType.HASHTAG))
                }

                updateState(
                    note = currentNote.copy(listOfNoteItem = updatedList),
                    openBottomSheet = false
                )
            }

            is AddNotesScreenAction.OnAddPopupActions -> {
                updateState(
                    openBottomSheet = true
                )
            }

            is AddNotesScreenAction.OnAddAttachments -> {
                action.attachemts
                val currentNote = _addNoteItemList.value.note
                val updatedList = currentNote.listOfNoteItem.toMutableList().apply {
                    add(
                        NoteItem(
                            noteAttachments = action.attachemts,
                            type = NoteItemType.ATTACHMENT
                        )
                    )
                }

                updateState(note = currentNote.copy(listOfNoteItem = updatedList))
            }
        }
    }

    private fun updateState(
        note: Note = _addNoteItemList.value.note,
        snackBarText: String = "",
        isLoading: Boolean = false,
        isNewNoteAdded: Boolean = false,
        isFabIconVisible: Boolean = _addNoteItemList.value.isFabIconVisible,
        openBottomSheet: Boolean = false,
        showPopup: Boolean = false,
    ) {
        _addNoteItemList.update {
            it.copy(
                note = note,
                snackBarText = snackBarText,
                isLoading = isLoading,
                isNewNoteAdded = isNewNoteAdded,
                isFabIconVisible = isFabIconVisible,
                showBottomSheet = openBottomSheet,
                showPopup = showPopup
            )
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
