package com.aki.notesapp.feature.filepicker

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.aki.notesapp.feature.filepicker.action.FilePickerViewModelAction
import com.aki.notesapp.feature.filepicker.model.FilePickerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FilePickerViewModel @Inject constructor() : ViewModel() {


    private val _listOfPickedUri = MutableStateFlow(FilePickerState(listOf()))
    val listOfPickedUri = _listOfPickedUri.asStateFlow()


    fun onAction(onFilePicked: FilePickerViewModelAction) {
        when (onFilePicked) {
            is FilePickerViewModelAction.OnFilePicked -> {
                update(
                    listOfFile = onFilePicked.listOfFile,
                    filePickerType = _listOfPickedUri.value.fileType
                )
            }
        }
    }

    private fun update(listOfFile: List<Uri>, filePickerType: String) {
        _listOfPickedUri.update {
            it.copy(
                listOfFile = listOfFile,
                fileType = filePickerType,
                isMultipleEnabled = false
            )
        }
    }


}