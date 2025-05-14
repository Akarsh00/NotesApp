package com.aki.notesapp.feature.filepicker.model

enum class FilePickerType(val imageMimeType: String) {
    IMAGE("image/*"),
    VIDEO("video/*"),
    PDF("application/pdf"),
}