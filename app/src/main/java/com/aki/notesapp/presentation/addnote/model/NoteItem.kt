package com.aki.notesapp.presentation.addnote.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val lisOfNoteItem: List<NoteItem>

)

data class NoteItem(
    var noteText: String = "",
    var hint: String = "",
    val isEditable: Boolean = false,
    val type: NoteItemType = NoteItemType.EMPTY
)


enum class NoteItemType {
    EMPTY,
    DATE,
    COMMENT,
    TITLE,
}

fun createNoteItemList(): MutableList<NoteItem> {
    val listItems = mutableListOf<NoteItem>(
        NoteItem(
            noteText = "",
            hint = "Enter task ame",
            isEditable = false,
            type = NoteItemType.TITLE
        ),
        NoteItem(
            noteText = "",
            hint = "Enter Detail description",
            isEditable = false,
            type = NoteItemType.COMMENT
        ),
        NoteItem(
            noteText = "",
            hint = "Add Date",
            isEditable = false,
            type = NoteItemType.DATE
        )

    )
    return listItems

}
fun getItemList(): MutableList<NoteItem> {
    val listItems = mutableListOf<NoteItem>(
        NoteItem(
            noteText = "${Random.nextInt(200)} This is task name",
            hint = "",
            isEditable = false,
            type = NoteItemType.TITLE
        ),
        NoteItem(
            noteText = "Item ${Random.nextInt(200)} This is the detailed description of this task",
            hint = "",
            isEditable = false,
            type = NoteItemType.COMMENT
        ),
        NoteItem(
            noteText = "",
            hint = "Add Date",
            isEditable = false,
            type = NoteItemType.DATE
        )

    )
    return listItems

}
