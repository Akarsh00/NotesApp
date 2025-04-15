package com.aki.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.aki.notesapp.presentation.addnote.AddNoteRoot
import com.aki.notesapp.presentation.filepicker.FilePickerScreen
import com.aki.notesapp.presentation.shownotes.ShowNotesListScreen


@Composable
fun AppNavigation(rememberNavHostController: NavHostController) {

    NavHost(rememberNavHostController, startDestination = NoteListScreenNav) {

        composable<NoteListScreenNav> {

            ShowNotesListScreen(openAddNoteScreen = { noteId ->
                rememberNavHostController.navigate(
                    AddNoteScreenNav(noteId)
                )
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
            FilePickerScreen(navController = rememberNavHostController)
        }
    }

}