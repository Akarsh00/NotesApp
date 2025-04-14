package com.aki.notesapp.common

import com.aki.notesapp.R
import com.aki.notesapp.presentation.addnote.model.NoteItemType

fun getIconFromNoteType(noteType: NoteItemType) =
     when (noteType) {
        NoteItemType.COMMENT -> R.drawable.ic_comments
        NoteItemType.HASHTAG -> R.drawable.ic_hashtags
        NoteItemType.TITLE -> R.drawable.ic_note_title
        else -> null
    }

