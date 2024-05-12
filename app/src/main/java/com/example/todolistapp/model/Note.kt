package com.example.todolistapp.model

import android.icu.text.CaseMap.Title
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "notes")
@Parcelize

data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val noteTitle: String,
    val noteDesc:String,
    val priority: String,
    val date: String
):Parcelable
