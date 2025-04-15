package com.aki.notesapp.presentation.shownotes.action

sealed interface NotesScreenAction {
    data class NotesScreenExpandCollapseClicked(val noteId: Long) : NotesScreenAction
}