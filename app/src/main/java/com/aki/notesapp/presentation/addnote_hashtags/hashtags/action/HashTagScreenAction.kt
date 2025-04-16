package com.aki.notesapp.presentation.addnote_hashtags.hashtags.action

sealed interface HashTagScreenAction {
    data class OnTextChange(val hashTagsText: String) : HashTagScreenAction
}