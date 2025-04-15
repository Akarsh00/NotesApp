package com.aki.notesapp.presentation.addnote_popup.popupnote.state

import com.aki.notesapp.db.model.NoteItemType

data class OtherOptionState(val type: NoteItemType, var isSelected: Boolean = false)


