package com.aki.notesapp.presentation.addnote_hashtags.hashtags.state

data class HashTagsModel(
    val hashTagText: String = "",
    val hashTagList: List<String> = listOf()
)