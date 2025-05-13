package com.aki.notesapp.feature.show_notes.presentation.action

sealed interface NotesScreenViewModelAction {
    data class NotesScreenExpandCollapseClicked(val noteId: Long) : NotesScreenViewModelAction
}