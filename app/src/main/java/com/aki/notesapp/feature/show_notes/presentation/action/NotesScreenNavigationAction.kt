package com.aki.notesapp.feature.show_notes.presentation.action

sealed interface NotesScreenNavigationAction {
    data class NotesScreenToAddNote(val noteId: Long?) : NotesScreenNavigationAction
    data class NotesScreenToImageScreen(val noteId: Long) : NotesScreenNavigationAction
}