package com.aki.notesapp.presentation.notesscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aki.notesapp.db.NoteDatabaseProvider
import com.aki.notesapp.presentation.EmptyNoteViews
import com.aki.notesapp.presentation.addnote.model.Note
import com.aki.notesapp.presentation.addnote.model.NoteItemType
import com.aki.notesapp.presentation.addnote.model.getItemList
import com.aki.notesapp.presentation.notesscreen.model.NotesScreenAction
import com.aki.notesapp.ui.theme.SoftRed

@Composable
fun ShowNotesRoot(modifier: Modifier = Modifier, openAddNoteScreen: (Long?) -> Unit) {

    val viewModel: ShowTaskScreenViewModel = viewModel(
        factory = ShowNotesScreenViewModelFactory(
            NoteDatabaseProvider.getDatabase(LocalContext.current).notesDao()
        )
    )
    val notesItem by viewModel.taskList.collectAsState(listOf())

    Scaffold(floatingActionButton = {
        FloatingActionButton({
            openAddNoteScreen.invoke(null)
        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
            )
        }
    }) { innerPadding ->
        ShowTasks(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            listOfNote = notesItem,
            onAction = viewModel::onAction,
            openAddNoteScreen = { openAddNoteScreen.invoke(it) })
    }

}


@Composable
fun HeaderNote(
    modifier: Modifier = Modifier,
    note: Note,
    onAction: (NotesScreenAction) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp, max = 500.dp)
            .background(color = Color(0xFFD9D9D9))
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = note.id.toString(),
            modifier = Modifier.widthIn(70.dp),
            textAlign = TextAlign.Center
        )
        VerticalDivider(
            color = SoftRed
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = if (note.expanded) {
                "Collapse"
            } else {
                "Read All"
            },
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = Color(0xFF0075FF),
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    onAction(NotesScreenAction.NotesScreenExpandCollapseClicked(noteId = note.id))
                }
                .align(alignment = Alignment.CenterVertically))
    }
}

@Composable
fun NoteContent(
    modifier: Modifier = Modifier,
    note: Note,
    onAction: (NotesScreenAction) -> Unit,
    openAddNoteScreen: (Long?) -> Unit
) {
    note.listOfNoteItem?.forEach { noteContent ->
        Row(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 50.dp, max = 500.dp)
                .height(IntrinsicSize.Max)
                .clickable(onClick = {
                    openAddNoteScreen.invoke(note.id)
                }),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)

        ) {
            if (noteContent.type == NoteItemType.TITLE || noteContent.type == NoteItemType.COMMENT) {
                Box(modifier = Modifier.widthIn(70.dp), contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.AccountBox, contentDescription = null)
                }
                VerticalDivider(
                    color = SoftRed
                )
                Text(
                    noteContent.noteText,
                    maxLines = if (note.expanded) {
                        Int.MAX_VALUE
                    } else {
                        1
                    },
                    overflow = TextOverflow.Ellipsis,
                )

            } else if (noteContent.type == NoteItemType.DATE) {
                Box(modifier = Modifier.widthIn(70.dp), contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                }
                VerticalDivider(
                    color = SoftRed
                )
            }

        }
        HorizontalDivider(color = Color(0xFFCAD1EB))

    }

}

@Composable
fun ShowTasks(
    modifier: Modifier = Modifier,
    listOfNote: List<Note>,
    onAction: (NotesScreenAction) -> Unit,
    openAddNoteScreen: (Long?) -> Unit

) {
    LazyColumn(modifier = modifier) {
        items(listOfNote.reversed()) { note ->
            HeaderNote(note = note, onAction = onAction)
            HorizontalDivider(color = Color(0xFFCAD1EB))
            NoteContent(
                note = note,
                onAction = onAction,
                openAddNoteScreen = { openAddNoteScreen.invoke(it) })
            HorizontalDivider(color = Color(0xFFCAD1EB))
        }
        items(5) {
            EmptyNoteViews()
        }
    }
}


@PreviewScreenSizes
@Preview(showSystemUi = true)
@Composable
fun ShowNotesPreview() {
    var listOfNotes = listOf(Note(0, getItemList()), Note(1, getItemList()), Note(2, getItemList()))
    Scaffold(modifier = Modifier) { paddingValues ->
        ShowTasks(modifier = Modifier.padding(paddingValues), listOfNote = listOfNotes, onAction = {

        }, openAddNoteScreen = {})

    }
}