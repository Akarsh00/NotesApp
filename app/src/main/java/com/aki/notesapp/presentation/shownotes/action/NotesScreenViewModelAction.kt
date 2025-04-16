package com.aki.notesapp.presentation.shownotes.action

sealed interface NotesScreenViewModelAction {
    data class NotesScreenExpandCollapseClicked(val noteId: Long) : NotesScreenViewModelAction
}