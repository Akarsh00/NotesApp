package com.aki.notesapp.feature.filepicker.action

import android.net.Uri

sealed interface FilePickerViewModelAction {
    data class OnFilePicked(val listOfFile: List<Uri>) : FilePickerViewModelAction
}