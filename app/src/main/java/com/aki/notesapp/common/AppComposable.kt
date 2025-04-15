package com.aki.notesapp.common

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun rememberGenericFilePicker(
    mimeType: String = "*/*",
    multiple: Boolean = false,
    onFilesPicked: (List<Uri>) -> Unit
): () -> Unit {
    // Single file picker
    val singlePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onFilesPicked(listOf(it)) }
    }

    // Multiple file picker
    val multiplePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        onFilesPicked(uris)
    }

    // Return appropriate lambda
    return {
        if (multiple) {
            multiplePicker.launch(mimeType)
        } else {
            singlePicker.launch(mimeType)
        }
    }
}


//Return data to previous screen
@Composable
fun <T> rememberNavResult(
    key: String,
    navController: NavHostController,
    onResult: (T) -> Unit
) {
    val currentEntry = navController.currentBackStackEntryAsState().value

    LaunchedEffect(currentEntry) {
        val savedStateHandle = currentEntry?.savedStateHandle
        val result = savedStateHandle?.get<T>(key)
        if (result != null) {
            onResult(result)
            savedStateHandle.remove<T>(key) // Clear it for one-time use
        }
    }
}




