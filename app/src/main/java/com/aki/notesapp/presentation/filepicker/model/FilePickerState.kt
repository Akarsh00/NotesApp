package com.aki.notesapp.presentation.filepicker.model

import android.net.Uri

data class FilePickerState(
    val listOfFile: List<Uri>,
    val fileType: String = FilePickerType.IMAGE.imageMimeType,
    val isMultipleEnabled: Boolean = true
)
