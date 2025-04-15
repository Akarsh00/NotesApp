package com.aki.notesapp.common

import com.aki.notesapp.db.model.NoteItem
import com.aki.notesapp.db.model.NoteItemType
import com.aki.notesapp.presentation.addnote_popup.popupnote.state.OtherOptionState
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
        OtherOptionState(NoteItemType.HASHTAG, false),
        OtherOptionState(NoteItemType.ATTACHMENT, false),
        OtherOptionState(NoteItemType.COMMENT, false),
        OtherOptionState(NoteItemType.HASHTAG, false),
        OtherOptionState(NoteItemType.ATTACHMENT, false),
        OtherOptionState(NoteItemType.COMMENT, false),
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