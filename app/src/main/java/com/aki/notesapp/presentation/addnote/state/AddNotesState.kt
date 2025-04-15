package com.aki.notesapp.presentation.addnote.state

import com.aki.notesapp.common.listOfPopupAddNotesAction
import com.aki.notesapp.db.model.Note
import com.aki.notesapp.presentation.addnote_popup.popupnote.state.OtherOptionState

data class AddNotesState(
    var note: Note,
    var isLoading: Boolean = true,
    var snackBarText: String = "",
    var isNewNoteAdded: Boolean = false,
    var isFabIconVisible: Boolean = true,
    var showPopup: Boolean = false,
    var showBottomSheet: Boolean = false,
    var otherOptionList: List<OtherOptionState> = listOfPopupAddNotesAction(),
)