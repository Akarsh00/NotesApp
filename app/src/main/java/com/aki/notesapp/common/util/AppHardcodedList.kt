package com.aki.notesapp.common.util

import com.aki.notesapp.common.domain.model.NoteItem
import com.aki.notesapp.common.domain.model.NoteItemType
import com.aki.notesapp.feature.popupnote.state.OtherOptionState
import com.aki.notesapp.feature.popupnote.state.OtherOptionType
import kotlin.random.Random


fun createNoteItemList(): MutableList<NoteItem> {
    val listItems = mutableListOf(
        NoteItem(
            noteText = "",
            hint = "Enter task ame",
            isEditable = false,
            type = NoteItemType.TITLE
        ),
        NoteItem(
            noteText = "",
            hint = "Enter Detail description",
            isEditable = false,
            type = NoteItemType.DESCRIPTION
        )

    )
    return listItems

}


fun listOfPopupAddNotesAction(): List<OtherOptionState> =
    listOf(
        OtherOptionState(OtherOptionType.HASHTAGS, false),
        OtherOptionState(OtherOptionType.ATTACHMENT, false),
        OtherOptionState(OtherOptionType.COMMENT, false),
        OtherOptionState(OtherOptionType.HASHTAGS, false),
        OtherOptionState(OtherOptionType.ATTACHMENT, false),
        OtherOptionState(OtherOptionType.COMMENT, false),
    )

fun getItemListPreview(): MutableList<NoteItem> {
    val listItems = mutableListOf(
        NoteItem(
            noteText = "${Random.nextInt(200)} This is task name",
            hint = "",
            isEditable = false,
            type = NoteItemType.TITLE
        ),
        NoteItem(
            noteText = "Item ${Random.nextInt(200)} This is the detailed description of this task",
            hint = "",
            isEditable = false,
            type = NoteItemType.DESCRIPTION
        ),

        NoteItem(
            noteText = "",
            hint = "",
            isEditable = false,
            type = NoteItemType.HASHTAG,
            hashTags = listOf("workout", "game", "new workout")
        )
    )
    return listItems

}