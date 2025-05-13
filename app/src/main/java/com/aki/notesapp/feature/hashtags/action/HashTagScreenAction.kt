package com.aki.notesapp.feature.hashtags.action

sealed interface HashTagScreenAction {
    data class OnTextChange(val hashTagsText: String) : HashTagScreenAction
}