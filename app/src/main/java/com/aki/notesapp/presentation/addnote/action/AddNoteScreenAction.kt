package com.aki.notesapp.presentation.addnote.action

import com.aki.notesapp.db.model.NoteAttachment
import com.aki.notesapp.db.model.NoteItem
import com.aki.notesapp.presentation.addnote_popup.popupnote.state.OtherOptionState

sealed interface AddNotesScreenAction {
    data object OnSaveNoteAction : AddNotesScreenAction
    data object OnBottomSheetCloseAction : AddNotesScreenAction
    data class OnNotesTextChange(val note: NoteItem, val newNumber: String) : AddNotesScreenAction
    data object OnFabClicked : AddNotesScreenAction
    data class OnAddHashTags(val hashTags : List<String>) : AddNotesScreenAction
    data class OnAddAttachments(val attachemts : List<NoteAttachment>) : AddNotesScreenAction
    data class OnAddPopupActions(val otherOptions : OtherOptionState) : AddNotesScreenAction
}