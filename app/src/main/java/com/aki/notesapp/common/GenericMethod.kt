package com.aki.notesapp.common

import com.aki.notesapp.R
import com.aki.notesapp.db.model.NoteItemType
import com.aki.notesapp.presentation.addnote_popup.popupnote.state.OtherOptionType

fun getIconFromNoteType(noteType: NoteItemType) =
     when (noteType) {
        NoteItemType.DESCRIPTION -> R.drawable.ic_description
        NoteItemType.HASHTAG -> R.drawable.ic_hashtags
        NoteItemType.ATTACHMENT -> R.drawable.ic_attachment
        NoteItemType.TITLE -> R.drawable.ic_note_title
        NoteItemType.COMMENT -> R.drawable.ic_comments
        NoteItemType.DATE -> R.drawable.ic_note_title
        else -> null
    }

fun iconFromAddNoteOtherOption(otherOptionType: OtherOptionType) =
     when (otherOptionType) {
         OtherOptionType.HASHTAGS -> R.drawable.ic_hashtags
         OtherOptionType.ATTACHMENT -> R.drawable.ic_attachment
         OtherOptionType.COMMENT -> R.drawable.ic_comments
        else -> null
    }

