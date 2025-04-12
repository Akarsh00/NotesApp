package com.aki.notesapp.presentation.addnote.more.model

data class AddMoreState(
    val hashTagText: String = "",
    val hashTagList: List<String> = listOf()
)