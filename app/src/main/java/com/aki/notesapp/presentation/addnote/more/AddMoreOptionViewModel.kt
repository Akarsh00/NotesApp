package com.aki.notesapp.presentation.addnote.more

import androidx.lifecycle.ViewModel
import com.aki.notesapp.presentation.addnote.more.model.AddMoreAction
import com.aki.notesapp.presentation.addnote.more.model.AddMoreState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddMoreOptionViewModel : ViewModel() {
    private val _addHashTagState = MutableStateFlow(AddMoreState())
    val addHashTagState = _addHashTagState.asStateFlow()

    fun onAction(action: AddMoreAction) {
        when (action) {
            is AddMoreAction.OnTextChange -> {
                update(action.hashTagsText)
            }

            is AddMoreAction.OnHashTagsAdded -> {}
        }
    }

    private fun update(text: String){
        _addHashTagState.update { it.copy(hashTagText = text, hashTagList = text.split(",")) }
    }
}