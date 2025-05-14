package com.aki.notesapp.feature.filepicker.action

import android.net.Uri


sealed interface FilePickerNavigationAction {
    data object OnBackPressed : FilePickerNavigationAction
    data class OnSubmit(val listOfFile:List<Uri>) : FilePickerNavigationAction
}