package com.aki.notesapp.presentation.addnote_popup.popupnote.state


data class OtherOptionState(val type: OtherOptionType, var isSelected: Boolean = false)

enum class OtherOptionType{
    HASHTAGS,ATTACHMENT,COMMENT
}
