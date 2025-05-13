package com.aki.notesapp.feature.add_notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aki.notesapp.common.util.createNoteItemList
import com.aki.notesapp.feature.add_notes.presentation.action.AddNotesScreenAction
import com.aki.notesapp.feature.add_notes.presentation.state.AddNotesState
import com.aki.notesapp.common.domain.model.NoteItem
import com.aki.notesapp.common.domain.model.NoteItemType
import com.aki.notesapp.feature.add_notes.data.repositoryimpl.AddNoteRepositoryImpl
import com.aki.notesapp.feature.add_notes.domain.model.Note
import com.aki.notesapp.feature.add_notes.domain.repository.AddNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(private val repository: AddNoteRepository) :
    ViewModel() {


    private val _addNoteEntityItemList = MutableStateFlow(
        AddNotesState(noteEntity = Note(listOfNoteItem = createNoteItemList()))
    )
    val addNoteItemList = _addNoteEntityItemList.asStateFlow()

    fun loadNoteById(id: Long) {
        viewModelScope.launch {
            val note = repository.getNoteWithId(id)
            updateState(note)
        }
    }


    fun updateNoteItem(noteItem: NoteItem, newText: String) {
        val currentNote = _addNoteEntityItemList.value.noteEntity
        val updatedItems = currentNote.listOfNoteItem.map {
            if (it == noteItem) it.copy(noteText = newText) else it
        }
        updateState(note = currentNote.copy(listOfNoteItem = updatedItems))
    }


    fun onAction(action: AddNotesScreenAction) {
        when (action) {
            AddNotesScreenAction.OnFabClicked -> {}

            is AddNotesScreenAction.OnNotesTextChange -> {
                updateNoteItem(action.note, action.newNumber)
            }

            AddNotesScreenAction.OnSaveNoteAction -> {
                val listToSave =
                    addNoteItemList.value.noteEntity.listOfNoteItem.filter { it.noteText.isNotEmpty() || it.hashTags.isNotEmpty() || it.noteAttachments.isNotEmpty() }

                updateState(
                    note = Note(
                        id = addNoteItemList.value.noteEntity.id,
                        listOfNoteItem = listToSave
                    ), snackBarText = "Notes Saved"
                )

                saveNote()

            }

            AddNotesScreenAction.OnBottomSheetCloseAction -> {
                updateState(
                    openBottomSheet = false
                )
            }

            is AddNotesScreenAction.OnAddHashTags -> {
                val currentNote = _addNoteEntityItemList.value.noteEntity
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
                val currentNote = _addNoteEntityItemList.value.noteEntity
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

    private fun saveNote() {
        viewModelScope.launch(Dispatchers.IO) {
            if (addNoteItemList.value.noteEntity.listOfNoteItem.isNotEmpty()) {
                repository.saveNote(addNoteItemList.value.noteEntity)
            }
        }
    }

    private fun updateState(
        note: Note = _addNoteEntityItemList.value.noteEntity,
        snackBarText: String = "",
        isLoading: Boolean = false,
        isNewNoteAdded: Boolean = false,
        isFabIconVisible: Boolean = _addNoteEntityItemList.value.isFabIconVisible,
        openBottomSheet: Boolean = false,
        showPopup: Boolean = false,
    ) {
        _addNoteEntityItemList.update {
            it.copy(
                noteEntity = note,
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

