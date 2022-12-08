package com.muiedhossain.notepad_kotlin.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*
import com.muiedhossain.notepad_kotlin.Model.NoteModel

@Dao
interface NotesDao {

    //data with the same id we will ignore that conflict
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note : NoteModel)

    @Update
    fun update(note : NoteModel)

    @Delete
    fun delete(note : NoteModel)

    @Query("Select * from notesTable order by id ASC")
    fun getAllNotes() : LiveData<List<NoteModel>>

}