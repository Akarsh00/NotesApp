package com.aki.notesapp.feature.hashtags

import androidx.lifecycle.ViewModel
import com.aki.notesapp.feature.hashtags.action.HashTagScreenAction
import com.aki.notesapp.feature.hashtags.state.HashTagsModel
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