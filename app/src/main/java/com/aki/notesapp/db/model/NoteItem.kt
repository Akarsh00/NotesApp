package com.aki.notesapp.db.model

data class NoteItem(
    var noteText: String = "",
    var hint: String = "",
    var hashTags: List<String> = listOf(),
    var noteAttachments: List<NoteAttachment> = listOf(),
    val isEditable: Boolean = false,
    val type: NoteItemType = NoteItemType.EMPTY
)