package com.aki.notesapp.presentation.addnote_hashtags.hashtags

import androidx.lifecycle.ViewModel
import com.aki.notesapp.presentation.addnote_hashtags.hashtags.action.HashTagScreenAction
import com.aki.notesapp.presentation.addnote_hashtags.hashtags.state.HashTagsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddHashTagsViewModel : ViewModel() {
    private val _addHashTagState = MutableStateFlow(HashTagsModel())
    val addHashTagState = _addHashTagState.asStateFlow()

    fun onAction(action: HashTagScreenAction) {
        when (action) {
            is HashTagScreenAction.OnTextChange -> {
                update(action.hashTagsText)
            }
        }
    }
    private fun update(text: String){
        _addHashTagState.update { it.copy(hashTagText = text, hashTagList = text.split(",")) }
    }
}