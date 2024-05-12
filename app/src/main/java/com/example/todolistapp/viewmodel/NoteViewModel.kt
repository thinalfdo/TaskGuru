package com.example.todolistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.todolistapp.model.Note
import com.example.todolistapp.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NoteViewModel(app:Application,private val noteRepository: NoteRepository): AndroidViewModel(app){

    fun addNote(note: Note)=
        viewModelScope.launch {
        noteRepository.insertNote(note)
        }
    fun deleteNote(note: Note)=
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    fun updateNote(note: Note)=
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    fun getAllNotes()=noteRepository.getAllNotes()

    fun searchNote(query: String?)=
        noteRepository.searchNotes(query)
}