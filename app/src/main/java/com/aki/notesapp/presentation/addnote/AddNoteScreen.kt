package com.aki.notesapp.presentation.addnote

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
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
import com.aki.notesapp.presentation.addnote.model.NoteItem
import com.aki.notesapp.presentation.addnote.model.NoteItemType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aki.notesapp.db.NoteDatabaseProvider
import com.aki.notesapp.presentation.EmptyNoteViews
import com.aki.notesapp.presentation.HeaderToolbar
import com.aki.notesapp.presentation.addnote.model.AddNotesScreenAction
import com.aki.notesapp.presentation.addnote.model.AddNotesState
import com.aki.notesapp.presentation.addnote.model.Note
import com.aki.notesapp.presentation.addnote.model.createNoteItemList
import com.aki.notesapp.presentation.addnote.more.AddMoreOptionBottomSheet
import com.aki.notesapp.presentation.addnote.more.model.AddMoreAction
import com.aki.notesapp.ui.theme.LightGrayBlue
import com.aki.notesapp.ui.theme.SoftRed

@Composable
fun AddNoteRoot(
    modifier: Modifier = Modifier, taskId: Long? = null, onBackPressed: () -> Unit
) {
    val viewModel: AddNoteViewModel = viewModel(
        factory = AddNoteViewModelFactory(
            NoteDatabaseProvider.getDatabase(LocalContext.current).notesDao()
        )
    )
    LaunchedEffect(taskId) {
        taskId?.let { viewModel.loadTaskById(taskId) }
    }


    val addNoteState by remember {
        viewModel.addNoteItemList
    }


    AddNoteFullScreen(
        addNoteState, onAction = viewModel::onAction, onBackPressed = onBackPressed
    )

}

@Composable
private fun AddNoteFullScreen(
    addNoteState: AddNotesState, onAction: (AddNotesScreenAction) -> Unit, onBackPressed: () -> Unit
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
            title = "Add Task",
            arrowBack = Icons.Filled.ArrowBack,
            onBackPressed = onBackPressed
        )
    }, bottomBar = {
        BottomBar(onAction = onAction)

    }, floatingActionButton = {
        AddMoreFABOption(onAction = onAction)
    }, snackbarHost = {
        SnackbarHost(snackbarHostState)

    }) { innerPadding ->
        AddNoteLazyColumn(notesItem = addNoteState.note, innerPadding, onAction = onAction)
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
    notesItem: Note, innerPadding: PaddingValues, onAction: (AddNotesScreenAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding), userScrollEnabled = false
    ) {
        item {
            AddNoteHeader(modifier = Modifier.heightIn(min = 50.dp, max = 500.dp))
        }
        items(notesItem.listOfNoteItem) { noteRow ->
            AddNote(
                noteRow, modifier = Modifier.heightIn(50.dp, max = 500.dp)
            ) { action ->
                onAction.invoke(
                    action
                )
            }

        }
        items(10) {
            EmptyNoteViews()
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


        when (newNote.type) {
            NoteItemType.EMPTY -> {
                Box(modifier = Modifier.widthIn(70.dp))
                VerticalDivider(
                    color = SoftRed
                )

            }

            NoteItemType.TITLE, NoteItemType.COMMENT -> {

                Box(modifier = Modifier.widthIn(70.dp), contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.AccountBox, contentDescription = null)
                }
                VerticalDivider(
                    color = SoftRed
                )
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

            NoteItemType.DATE -> {
                Box(modifier = Modifier.widthIn(70.dp), contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)

                }
                VerticalDivider(
                    color = SoftRed
                )

            }

            NoteItemType.HASHTAG -> {}
        }
    }
    HorizontalDivider(color = LightGrayBlue)

}


@PreviewScreenSizes
@Preview
@Composable
fun AddNotePreview() {

    AddNoteFullScreen(
        AddNotesState(
            note = Note(listOfNoteItem = createNoteItemList()),
            isLoading = false,
            isNewNoteAdded = false,
        ), onAction = {}) {

    }
}