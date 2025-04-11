package com.aki.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aki.notesapp.navigation.AppNavigation
import com.aki.notesapp.presentation.addnote.AddNoteRoot
import com.aki.notesapp.presentation.notesscreen.ShowNotesRoot
import com.aki.notesapp.ui.theme.NotesAppTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                val appNavController = rememberNavController()
                AppNavigation(appNavController)
            }
        }
    }
}


