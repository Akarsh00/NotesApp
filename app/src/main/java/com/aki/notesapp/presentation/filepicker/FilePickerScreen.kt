package com.aki.notesapp.presentation.filepicker

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aki.notesapp.R
import com.aki.notesapp.common.HeaderToolbar
import com.aki.notesapp.common.dashedBorder
import com.aki.notesapp.common.rememberGenericFilePicker

@Composable
fun FilePickerScreen(navController: NavController?, modifier: Modifier = Modifier) {

    var pickedUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var currentMime by remember { mutableStateOf("image/*") }
    var isMultiple by remember { mutableStateOf(false) }

    val pickFiles = rememberGenericFilePicker(
        mimeType = currentMime,
        multiple = isMultiple
    ) { uris ->
        pickedUris = uris
    }


    Scaffold(topBar = {
        HeaderToolbar(title = "Attachment", arrowBack = Icons.Filled.ArrowBack, onIconClicked = {})
    }, bottomBar = { BottomBar() }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),

            ) {

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable {
                        currentMime = "image/*"
                        isMultiple = true
                        pickFiles()

                    }
                    .dashedBorder(
                        color = Color(0xFFC3CCD5),
                        strokeWidth = 1.dp,
                        dashLength = 10.dp,
                        gapLength = 6.dp,
                        cornerRadius = 8.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Image(
                        painter = painterResource(R.drawable.add_image),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Upload File Here",
                        color = Color(0xFF0075FF),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 16.dp)
                    )
                }


            }


        }


    }
}

@Composable
fun BottomBar(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = {

                }, modifier = Modifier
                    .height(44.dp)
                    .weight(1f), shape = RoundedCornerShape(8.dp)

            ) {
                Text(
                    "Cancel",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF2A333C)
                )
            }
            Button(
                onClick = {

                },
                modifier = Modifier
                    .height(44.dp)
                    .weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6900))

            ) {
                Text("Save", fontSize = 13.sp, fontWeight = FontWeight.W400)
            }
        }
    }
}

//}
//
//@Composable
//fun FilePickerScreen(navController: NavController? = null, modifier: Modifier = Modifier) {
//    var pickedUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
//    var currentMime by remember { mutableStateOf("application/pdf") }
//    var isMultiple by remember { mutableStateOf(false) }
//
//
//    val pickFiles = rememberGenericFilePicker(
//        mimeType = currentMime,
//        multiple = isMultiple
//    ) { uris ->
//        pickedUris = uris
//        navController?.previousBackStackEntry
//            ?.savedStateHandle
//            ?.set("picked_attachments", pickedUris)
//        navController?.popBackStack()
//    }
//
//    Column(modifier = modifier.padding(16.dp)) {
//        Text("Pick a file:", fontWeight = FontWeight.Bold)
//
//        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//            Button(onClick = {
//                currentMime = "image/*"
//                isMultiple = true
//                pickFiles()
//            }) { Text("Images") }
//
//            Button(onClick = {
//                currentMime = "video/*"
//                isMultiple = false
//                pickFiles()
//            }) { Text("Video") }
//
//            Button(onClick = {
//                currentMime = "application/pdf"
//                isMultiple = false
//                pickFiles()
//            }) { Text("PDF") }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        pickedUris.forEach { uri ->
//            Text("Selected: $uri", fontSize = 12.sp)
//        }
//    }
//}

@Preview
@Composable
fun FilePickerScreenPreview() {
    FilePickerScreen(navController = null)
}