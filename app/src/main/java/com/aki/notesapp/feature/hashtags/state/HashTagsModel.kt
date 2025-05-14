package com.aki.notesapp.feature.hashtags.state

data class HashTagsModel(
    val hashTagText: String = "",
    val hashTagList: List<String> = listOf()
)