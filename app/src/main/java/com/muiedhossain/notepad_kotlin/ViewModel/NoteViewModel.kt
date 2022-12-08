package com.muiedhossain.notepad_kotlin.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.muiedhossain.notepad_kotlin.Database.NoteDatabase
import com.muiedhossain.notepad_kotlin.Model.NoteModel
import com.muiedhossain.notepad_kotlin.Repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application){
    val allNotes : LiveData<List<NoteModel>>
    val repository : NoteRepository
    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }
    fun deleteNote(note: NoteModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }
    fun updateNote(note: NoteModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }
    fun addNote(note: NoteModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}