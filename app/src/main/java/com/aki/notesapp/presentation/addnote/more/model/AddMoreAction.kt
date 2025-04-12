package com.aki.notesapp.presentation.addnote.more.model

sealed interface AddMoreAction {
    data class OnHashTagsAdded(val addMoreState: AddMoreState) : AddMoreAction
    data class OnTextChange(val hashTagsText: String) : AddMoreAction{}
}