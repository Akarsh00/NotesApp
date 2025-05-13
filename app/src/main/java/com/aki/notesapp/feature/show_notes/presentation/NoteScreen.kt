package com.aki.notesapp.feature.show_notes.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aki.notesapp.common.util.AssistChipNoteItem
import com.aki.notesapp.common.util.EmptyNoteViews
import com.aki.notesapp.common.util.NotesAttachmentImage
import com.aki.notesapp.common.util.NotesItemIconImage
import com.aki.notesapp.common.util.getIconFromNoteType
import com.aki.notesapp.common.util.getItemListPreview
import com.aki.notesapp.common.data.dto.NoteEntity
import com.aki.notesapp.common.domain.model.NoteAttachment
import com.aki.notesapp.common.domain.model.NoteItemType
import com.aki.notesapp.feature.add_notes.domain.model.Note
import com.aki.notesapp.feature.show_notes.presentation.action.NotesScreenNavigationAction
import com.aki.notesapp.feature.show_notes.presentation.action.NotesScreenViewModelAction
import com.aki.notesapp.ui.theme.LightGrayBlue
import com.aki.notesapp.ui.theme.SoftRed

@Composable
fun ShowNotesListScreen(
    modifier: Modifier = Modifier, navigationAction: (NotesScreenNavigationAction) -> Unit
) {

    val viewModel: ShowNoteScreenViewModel = hiltViewModel()
    val notesItem by viewModel.noteEntityList.collectAsStateWithLifecycle(listOf())

    Scaffold(floatingActionButton = {
        FloatingActionButton({
            navigationAction(NotesScreenNavigationAction.NotesScreenToAddNote(noteId = null))
        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
            )
        }
    }) { innerPadding ->
        ShowNotes(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            listOfNoteEntity = notesItem,
            onAction = viewModel::onAction,
            navigationAction = navigationAction
        )
    }

}


@Composable
fun HeaderNote(
    modifier: Modifier = Modifier,
    note: Note,
    onAction: (NotesScreenViewModelAction) -> Unit,
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
                    onAction(NotesScreenViewModelAction.NotesScreenExpandCollapseClicked(noteId = note.id))
                }
                .align(alignment = Alignment.CenterVertically))
    }
}

@Composable
fun NoteContent(
    modifier: Modifier = Modifier,
    note: Note,
    onAction: (NotesScreenViewModelAction) -> Unit,
    openAddNoteScreen: (Long?) -> Unit
) {

    note.listOfNoteItem.forEach { newNote ->
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

            Box(
                modifier = Modifier
                    .widthIn(70.dp)
                    .fillMaxHeight(),
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

                NoteItemType.DESCRIPTION, NoteItemType.TITLE -> {
                    Text(
                        newNote.noteText,
                        maxLines = if (note.expanded) {
                            Int.MAX_VALUE
                        } else {
                            1
                        },
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                NoteItemType.COMMENT -> {
                }

                NoteItemType.ATTACHMENT -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        newNote.noteAttachments.forEach { item: NoteAttachment ->
                            NotesAttachmentImage(path = item.uri)
                        }
                    }

                }
            }
        }
        HorizontalDivider(color = LightGrayBlue)
    }

}

@Composable
fun ShowNotes(
    modifier: Modifier = Modifier,
    listOfNoteEntity: List<Note>,
    onAction: (NotesScreenViewModelAction) -> Unit,
    navigationAction: (NotesScreenNavigationAction) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(listOfNoteEntity.reversed()) { note ->
            HeaderNote(note = note, onAction = onAction)
            HorizontalDivider(color = Color(0xFFCAD1EB))
            NoteContent(
                note = note, onAction = onAction, openAddNoteScreen = {
                    navigationAction(NotesScreenNavigationAction.NotesScreenToAddNote(noteId = it))
                })
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
    val listOfNoteEntities = listOf(
        Note(0, getItemListPreview()), Note(1, getItemListPreview()), Note(2, getItemListPreview())
    )
    Scaffold(modifier = Modifier) { paddingValues ->
        ShowNotes(
            modifier = Modifier.padding(paddingValues),
            listOfNoteEntity = listOfNoteEntities,
            onAction = {

            },
            navigationAction = {

            })

    }
}