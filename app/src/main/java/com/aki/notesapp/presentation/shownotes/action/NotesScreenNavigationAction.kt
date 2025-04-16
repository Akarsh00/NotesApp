package com.aki.notesapp.presentation.shownotes.action

sealed interface NotesScreenNavigationAction {
    data class NotesScreenToAddNote(val noteId: Long?) : NotesScreenNavigationAction
    data class NotesScreenToImageScreen(val noteId: Long) : NotesScreenNavigationAction
}