package com.aki.notesapp.feature.filepicker

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.aki.notesapp.R
import com.aki.notesapp.common.util.HeaderToolbar
import com.aki.notesapp.common.util.dashedBorder
import com.aki.notesapp.common.util.rememberGenericFilePicker
import com.aki.notesapp.feature.filepicker.action.FilePickerNavigationAction
import com.aki.notesapp.feature.filepicker.action.FilePickerViewModelAction

@Composable
fun FilePickerScreen(
    onActionNavigation: (FilePickerNavigationAction) -> Unit
) {

    val viewmodel: FilePickerViewModel = hiltViewModel()
    val filePickerState by viewmodel.listOfPickedUri.collectAsStateWithLifecycle()

    val pickFiles = rememberGenericFilePicker(
        mimeType = filePickerState.fileType,
        multiple = filePickerState.isMultipleEnabled
    ) { uris ->

        viewmodel.onAction(FilePickerViewModelAction.OnFilePicked(uris))
    }


    Scaffold(
        topBar = {
            HeaderToolbar(
                title = "Attachment",
                arrowBack = Icons.Filled.ArrowBack,
                onIconClicked = {})
        },
        bottomBar = {
            BottomBar(
                pickedUris = filePickerState.listOfFile,
                onActionNavigation = onActionNavigation
            )
        }) { paddingValues ->
        Column(
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
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(4.dp)
            ) {
                filePickerState.listOfFile.forEach { imagePath ->
                    Image(
                        painter = rememberAsyncImagePainter(imagePath),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(4.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }


        }


    }
}

@Composable
fun BottomBar(
    pickedUris: List<Uri>,
    modifier: Modifier = Modifier,
    onActionNavigation: (FilePickerNavigationAction) -> Unit,
) {
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
                    onActionNavigation(FilePickerNavigationAction.OnBackPressed)

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

                    onActionNavigation(FilePickerNavigationAction.OnSubmit(pickedUris))

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

@Preview
@Composable
fun FilePickerScreenPreview() {
    FilePickerScreen {}
}