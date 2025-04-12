package com.aki.notesapp.presentation.notesscreen.model

sealed interface NotesScreenAction {
    data class NotesScreenExpandCollapseClicked(val noteId: Long) : NotesScreenAction
}