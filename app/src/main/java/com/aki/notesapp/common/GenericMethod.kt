package com.aki.notesapp.common

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.aki.notesapp.R
import com.aki.notesapp.db.model.NoteItemType
import com.aki.notesapp.presentation.addnote_popup.popupnote.state.OtherOptionType


fun insertImageIntoMediaStore(context: Context?, inputUri: Uri): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "note_image_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/NotesApp")
    }

    val resolver = context?.contentResolver
    val uri = resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let { outputUri ->
        resolver.openOutputStream(outputUri)?.use { outputStream ->
            resolver.openInputStream(inputUri)?.copyTo(outputStream)
        }
    }

    return uri
}

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
    }

