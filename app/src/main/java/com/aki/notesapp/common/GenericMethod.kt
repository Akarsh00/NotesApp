package com.aki.notesapp.common

import com.aki.notesapp.R
import com.aki.notesapp.db.model.NoteItemType

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

