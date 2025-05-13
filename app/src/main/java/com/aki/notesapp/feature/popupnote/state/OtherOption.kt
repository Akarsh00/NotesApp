package com.aki.notesapp.feature.popupnote.state


data class OtherOptionState(val type: OtherOptionType, var isSelected: Boolean = false)

enum class OtherOptionType{
    HASHTAGS,ATTACHMENT,COMMENT
}
