package com.muiedhossain.notepad_kotlin.Repository

import androidx.lifecycle.LiveData
import com.muiedhossain.notepad_kotlin.Dao.NotesDao
import com.muiedhossain.notepad_kotlin.Model.NoteModel

/*kotlin e eitar mane constructor create kore parameter*/
class NoteRepository(private val notesDao: NotesDao) {
    val allNotes: LiveData<List<NoteModel>> = notesDao.getAllNotes()

     fun insert(note: NoteModel) {
        notesDao.insert(note)
    }

    fun update(note: NoteModel) {
        notesDao.update(note)
    }

    fun delete(note: NoteModel) {
        notesDao.delete(note)
    }
}