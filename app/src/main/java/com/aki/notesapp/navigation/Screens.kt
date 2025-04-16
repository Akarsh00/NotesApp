package com.aki.notesapp.navigation

import kotlinx.serialization.Serializable

@Serializable
data class AddNoteScreenNav(val id: Long?)

@Serializable
object NoteListScreenNav

@Serializable
object  AddAttachmentScreenNav
