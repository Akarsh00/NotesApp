package com.aki.notesapp.presentation.addnote.model

data class AddNotesState(
    var note: Note,
    var isLoading: Boolean = true,
    var snackBarText: String = "",
    var isNewNoteAdded: Boolean = false,
    var isFabIconVisible: Boolean = true,
    var showBottomSheet: Boolean = false,
)