package com.aki.notesapp.presentation.addnote.model

sealed interface AddNotesScreenAction {

    data object OnSaveNoteAction : AddNotesScreenAction
    data class OnNotesTextChange(val note: NoteItem, val newNumber: String) : AddNotesScreenAction
    data object OnFabClicked : AddNotesScreenAction

}