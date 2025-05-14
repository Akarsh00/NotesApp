package com.aki.notesapp.feature.add_notes.presentation

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aki.notesapp.common.domain.model.NoteItem
import com.aki.notesapp.common.domain.model.NoteItemType
import androidx.navigation.NavHostController
import com.aki.notesapp.common.util.AssistChipNoteItem
import com.aki.notesapp.common.util.EmptyNoteViews
import com.aki.notesapp.common.util.HeaderToolbar
import com.aki.notesapp.common.util.NotesAttachmentImage
import com.aki.notesapp.common.util.NotesItemIconImage
import com.aki.notesapp.common.util.createNoteItemList
import com.aki.notesapp.common.util.getIconFromNoteType
import com.aki.notesapp.common.util.insertImageIntoMediaStore
import com.aki.notesapp.common.util.rememberNavResult
import com.aki.notesapp.feature.add_notes.presentation.action.AddNotesScreenAction
import com.aki.notesapp.feature.add_notes.presentation.state.AddNotesState
import com.aki.notesapp.common.data.dto.NoteEntity
import com.aki.notesapp.common.domain.model.NoteAttachment
import com.aki.notesapp.feature.add_notes.domain.model.Note
import com.aki.notesapp.feature.hashtags.AddMoreOptionBottomSheet
import com.aki.notesapp.feature.popupnote.AddOtherOptionPopup
import com.aki.notesapp.feature.popupnote.state.OtherOptionType
import com.aki.notesapp.ui.theme.LightGrayBlue
import com.aki.notesapp.ui.theme.SoftRed

@Composable
fun AddNoteRoot(
    navController: NavHostController?,
    noteId: Long? = null,
    onBackPressed: () -> Unit,
    openAttachmentsScreen: () -> Unit

) {

    val viewModel: AddNoteViewModel = hiltViewModel()
    LaunchedEffect(noteId) {
        noteId?.let { viewModel.loadNoteById(noteId) }
    }


    val addNoteState by viewModel.addNoteItemList.collectAsStateWithLifecycle()
    val context = LocalContext.current

    navController?.let {
        rememberNavResult<List<Uri>>(
            key = "picked_attachments", navController = it
        ) { uriList ->
            val savedUris = uriList.mapNotNull { uri ->
                insertImageIntoMediaStore(context, uri)
            }

            val attachments = savedUris.map { savedUri ->
                NoteAttachment(
                    noteAttachment = NoteItemType.ATTACHMENT,
                    uri = savedUri.toString()
                )
            }

            viewModel.onAction(AddNotesScreenAction.OnAddAttachments(attachments))
        }
    }
    AddNoteFullScreen(
        addNoteState = addNoteState,
        onAction = viewModel::onAction,
        onBackPressed = onBackPressed,
        openAttachmentsScreen = { openAttachmentsScreen.invoke() })

}

@Composable
private fun AddNoteFullScreen(
    modifier: Modifier = Modifier,
    addNoteState: AddNotesState,
    onAction: (AddNotesScreenAction) -> Unit,
    onBackPressed: () -> Unit,
    openAttachmentsScreen: () -> Unit

) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(addNoteState.snackBarText) {
        if (addNoteState.snackBarText.isNotEmpty()) {
            onBackPressed.invoke()
            snackbarHostState.showSnackbar(addNoteState.snackBarText)
        }
    }

    if (addNoteState.showBottomSheet) {
        AddMoreOptionBottomSheet(addNoteState, onSave = {
            onAction(AddNotesScreenAction.OnAddHashTags(it))

        }, onDismiss = {
            onAction(AddNotesScreenAction.OnBottomSheetCloseAction)
        })
    }
    Scaffold(topBar = {
        HeaderToolbar(
            title = "Add Note", arrowBack = Icons.Filled.ArrowBack, onIconClicked = onBackPressed
        )
    }, bottomBar = {
        BottomBar(onAction = onAction)

    }, floatingActionButton = {}, snackbarHost = {
        SnackbarHost(snackbarHostState)

    }) { innerPadding ->
        AddNoteLazyColumn(
            modifier = modifier,
            notesItem = addNoteState,
            innerPadding,
            onAction = onAction,
            openAttachmentsScreen = { openAttachmentsScreen.invoke() })
    }
}

@Composable
private fun AddMoreFABOption(onAction: (AddNotesScreenAction) -> Unit) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .border(2.dp, SoftRed, CircleShape),

        ) {
        FloatingActionButton(
            onClick = { onAction(AddNotesScreenAction.OnFabClicked) },
            contentColor = SoftRed,
            containerColor = Color.White,
            shape = CircleShape,
            modifier = Modifier.fillMaxSize() // Fill the bordered box
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
            )
        }
    }
}

@Composable
private fun AddNoteLazyColumn(
    modifier: Modifier = Modifier,
    notesItem: AddNotesState,
    innerPadding: PaddingValues,
    onAction: (AddNotesScreenAction) -> Unit,
    openAttachmentsScreen: () -> Unit

) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), userScrollEnabled = false
        ) {
            item {
//                AddNoteHeader(modifier = Modifier.heightIn(min = 50.dp, max = 500.dp))
            }
            items(notesItem.noteEntity.listOfNoteItem) { noteRow ->
                AddNote(
                    noteRow, modifier = Modifier.heightIn(50.dp, max = 500.dp)
                ) { action ->
                    onAction.invoke(
                        action
                    )
                }

            }
            items(25) {
                EmptyNoteViews()
            }

        }
        AddOtherOptionPopup(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            otherOptions = notesItem.otherOptionList
        ) { otherOptions ->
            when (otherOptions.type) {
                OtherOptionType.HASHTAGS -> {
                    onAction(AddNotesScreenAction.OnAddPopupActions(otherOptions))

                }

                OtherOptionType.ATTACHMENT -> {
                    openAttachmentsScreen.invoke()

                }

                OtherOptionType.COMMENT -> {

                }
            }

        }
    }

}


@Composable
private fun BottomBar(
    onAction: (AddNotesScreenAction) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                    onAction.invoke(AddNotesScreenAction.OnSaveNoteAction)
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

@Composable
fun AddNoteHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "12:20",
            modifier = Modifier
                .widthIn(70.dp)
                .padding(4.dp)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center
        )
        VerticalDivider(
            color = SoftRed
        )
    }
    HorizontalDivider(color = LightGrayBlue)


}

@Composable
fun AddNote(
    newNote: NoteItem, modifier: Modifier, onAction: (AddNotesScreenAction) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Box(
            modifier = Modifier.widthIn(min = 70.dp, max = 70.dp),
            contentAlignment = Alignment.Center
        ) {
            val icon = getIconFromNoteType(newNote.type)
            icon?.let {
                NotesItemIconImage(icon = it)
            }

        }

        VerticalDivider(
            color = SoftRed
        )

        when (newNote.type) {
            NoteItemType.EMPTY -> {}
            NoteItemType.DATE -> {}
            NoteItemType.HASHTAG -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    newNote.hashTags.forEach { chipText ->
                        AssistChipNoteItem(chipText)
                    }

                }


            }

            NoteItemType.TITLE, NoteItemType.DESCRIPTION -> {

                Box(
                    modifier = Modifier, contentAlignment = Alignment.CenterStart
                ) {
                    BasicTextField(
                        value = newNote.noteText,
                        onValueChange = { newText ->
                            onAction(
                                AddNotesScreenAction.OnNotesTextChange(
                                    newNote, newText
                                )
                            )
                        },
                        textStyle = TextStyle(fontSize = 18.sp, color = Color(0XFF556777)),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .background(Color.Transparent),
                    )

                    if (newNote.noteText.isEmpty()) {
                        Text(
                            text = newNote.hint, fontSize = 16.sp, color = Color(0XFF889AAA)
                        )
                    }
                }
            }

            NoteItemType.COMMENT -> {}
            NoteItemType.ATTACHMENT -> {
                newNote.noteAttachments.fastForEachIndexed { i, noteAttachment ->
                    NotesAttachmentImage(path = noteAttachment.uri)
                }


            }
        }
    }
    HorizontalDivider(color = LightGrayBlue)

}


@PreviewScreenSizes
@Preview
@Composable
fun AddNotePreview() {

    AddNoteFullScreen(
        addNoteState = AddNotesState(
            noteEntity = Note(listOfNoteItem = createNoteItemList()),
            isLoading = false,
            isNewNoteAdded = false,
        ), onBackPressed = {}, onAction = {}) {

    }
}