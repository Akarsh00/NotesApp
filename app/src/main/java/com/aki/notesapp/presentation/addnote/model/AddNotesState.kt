package com.aki.notesapp.presentation.addnote.model

data class AddNotesState(
    var note: Note,
    var isLoading: Boolean,
    var snackBarText: String = "",
    var isNewNoteAdded: Boolean = false,
)