package com.aki.notesapp.feature.add_notes.presentation.state

import com.aki.notesapp.common.util.listOfPopupAddNotesAction
import com.aki.notesapp.common.data.dto.NoteEntity
import com.aki.notesapp.feature.add_notes.domain.model.Note
import com.aki.notesapp.feature.popupnote.state.OtherOptionState

data class AddNotesState(
    var noteEntity: Note,
    var isLoading: Boolean = true,
    var snackBarText: String = "",
    var isNewNoteAdded: Boolean = false,
    var isFabIconVisible: Boolean = true,
    var showPopup: Boolean = false,
    var showBottomSheet: Boolean = false,
    var otherOptionList: List<OtherOptionState> = listOfPopupAddNotesAction(),
)