package com.aki.notesapp.db.dao.converter

import androidx.room.TypeConverter
import com.aki.notesapp.presentation.addnote.model.NoteItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NoteItemListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromNoteItemList(list: List<NoteItem>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toNoteItemList(json: String): List<NoteItem> {
        val type = object : TypeToken<List<NoteItem>>() {}.type
        return gson.fromJson(json, type)
    }
}
