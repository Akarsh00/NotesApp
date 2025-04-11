package com.aki.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.aki.notesapp.db.NoteDatabaseProvider
import com.aki.notesapp.presentation.addnote.AddNoteRoot
import com.aki.notesapp.presentation.addnote.AddNoteViewModel
import com.aki.notesapp.presentation.addnote.AddNoteViewModelFactory
import com.aki.notesapp.presentation.notesscreen.ShowNotesRoot


@Composable
fun AppNavigation(rememberNavHostController: NavHostController) {

    NavHost(rememberNavHostController, startDestination = NoteScreenNav) {

        composable<NoteScreenNav> {

            ShowNotesRoot {
                rememberNavHostController.navigate(AddNoteScreenNav(null))
            }
        }
        composable<AddNoteScreenNav> {
            val args = it.toRoute<AddNoteScreenNav>()
            AddNoteRoot(taskId = args.id){
                rememberNavHostController.navigateUp()
            }
        }
    }

}