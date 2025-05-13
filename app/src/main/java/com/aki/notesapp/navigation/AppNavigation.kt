package com.aki.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.aki.notesapp.feature.add_notes.presentation.AddNoteRoot
import com.aki.notesapp.feature.filepicker.FilePickerScreen
import com.aki.notesapp.feature.filepicker.action.FilePickerNavigationAction
import com.aki.notesapp.feature.show_notes.presentation.ShowNotesListScreen
import com.aki.notesapp.feature.show_notes.presentation.action.NotesScreenNavigationAction


@Composable
fun AppNavigation(rememberNavHostController: NavHostController) {

    NavHost(rememberNavHostController, startDestination = NoteListScreenNav) {

        composable<NoteListScreenNav> {

            ShowNotesListScreen(navigationAction = { action ->
                when (action) {
                    is NotesScreenNavigationAction.NotesScreenToAddNote -> {
                        rememberNavHostController.navigate(
                            AddNoteScreenNav(action.noteId)
                        )
                    }

                    is NotesScreenNavigationAction.NotesScreenToImageScreen -> {}

                }

            })
        }
        composable<AddNoteScreenNav> {
            val args = it.toRoute<AddNoteScreenNav>()
            AddNoteRoot(
                navController = rememberNavHostController,
                noteId = args.id,
                onBackPressed = {
                    rememberNavHostController.navigateUp()

                },
                openAttachmentsScreen = {
                    rememberNavHostController.navigate(AddAttachmentScreenNav)
                })
        }
        composable<AddAttachmentScreenNav> {
            FilePickerScreen { filePickerAction ->
                when (filePickerAction) {
                    FilePickerNavigationAction.OnBackPressed -> {
                        rememberNavHostController.navigateUp()
                    }

                    is FilePickerNavigationAction.OnSubmit -> {
                        if (filePickerAction.listOfFile.isNotEmpty()) {
                            rememberNavHostController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("picked_attachments", filePickerAction.listOfFile)
                            rememberNavHostController.popBackStack()
                        }
                    }
                }

            }
        }
    }

}

