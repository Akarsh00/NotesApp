package com.aki.notesapp.presentation.filepicker.model

enum class FilePickerType(val imageMimeType: String) {
    IMAGE("image/*"),
    VIDEO("video/*"),
    PDF("application/pdf"),
}