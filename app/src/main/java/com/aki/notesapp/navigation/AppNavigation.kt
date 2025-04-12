package com.aki.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.aki.notesapp.presentation.addnote.AddNoteRoot
import com.aki.notesapp.presentation.notesscreen.ShowNotesRoot
import com.aki.notesapp.presentation.notesscreen.model.NotesScreenAction


@Composable
fun AppNavigation(rememberNavHostController: NavHostController) {

    NavHost(rememberNavHostController, startDestination = NoteScreenNav) {

        composable<NoteScreenNav> {

            ShowNotesRoot(openAddNoteScreen = {
                rememberNavHostController.navigate(
                    AddNoteScreenNav(it)
                )
            })
        }
        composable<AddNoteScreenNav> {
            val args = it.toRoute<AddNoteScreenNav>()
            AddNoteRoot(taskId = args.id) {
                rememberNavHostController.navigateUp()
            }
        }
    }

}