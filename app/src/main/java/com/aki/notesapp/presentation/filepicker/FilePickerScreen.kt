package com.aki.notesapp.presentation.filepicker

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aki.notesapp.common.rememberGenericFilePicker

@Composable
fun FilePickerScreen(navController: NavController? = null, modifier: Modifier = Modifier) {
    var pickedUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var currentMime by remember { mutableStateOf("image/*") }
    var isMultiple by remember { mutableStateOf(false) }


    val pickFiles = rememberGenericFilePicker(
        mimeType = currentMime,
        multiple = isMultiple
    ) { uris ->
        pickedUris = uris
        navController?.previousBackStackEntry
            ?.savedStateHandle
            ?.set("picked_attachments", pickedUris)
        navController?.popBackStack()
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text("Pick a file:", fontWeight = FontWeight.Bold)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                currentMime = "image/*"
                isMultiple = true
                pickFiles()
            }) { Text("Images") }

            Button(onClick = {
                currentMime = "video/*"
                isMultiple = false
                pickFiles()
            }) { Text("Video") }

            Button(onClick = {
                currentMime = "application/pdf"
                isMultiple = false
                pickFiles()
            }) { Text("PDF") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        pickedUris.forEach { uri ->
            Text("Selected: $uri", fontSize = 12.sp)
        }
    }
}

@Preview
@Composable
fun FilePickerScreenPreview() {
    FilePickerScreen(modifier = Modifier.fillMaxSize())
}