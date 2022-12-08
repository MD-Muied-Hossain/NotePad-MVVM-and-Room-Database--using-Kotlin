package com.muiedhossain.notepad_kotlin.Activity

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.muiedhossain.notepad_kotlin.MainActivity
import com.muiedhossain.notepad_kotlin.Model.NoteModel
import com.muiedhossain.notepad_kotlin.R
import com.muiedhossain.notepad_kotlin.ViewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class InsertUpdateNoteActivity : AppCompatActivity() {

    lateinit var noteTitle : EditText
    lateinit var noteDescription : EditText
    lateinit var saveBtn : FloatingActionButton
    lateinit var noteViewModel : NoteViewModel
    var noteID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_update_note)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        noteTitle = findViewById(R.id.titleET)
        noteDescription = findViewById(R.id.noteDescriptionET)
        saveBtn = findViewById(R.id.saveNotesBtn)
        noteViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
            .getInstance(application)).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")){
            val note_Title = intent.getStringExtra("noteTitle")
            val note_Description = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID" , -1)

            noteTitle.setText(note_Title)
            noteDescription.setText(note_Description)
        }
        saveBtn.setOnClickListener {
            val noteTitle = noteTitle.text.toString()
            val noteDesc = noteDescription.text.toString()

            if(noteType.equals("Edit"))
            {
                if(noteTitle.isNotEmpty() && noteDesc.isNotEmpty()){
                    val simple_Time_And_Date_Formate = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate : String = simple_Time_And_Date_Formate.format(Date())
                    val updateNote = NoteModel(noteTitle,noteDesc,currentDate)
                    updateNote.id = noteID
                    noteViewModel.updateNote(updateNote)
                    Toast.makeText(this,"Note Updated Successfully ! ", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    this.finish()
                }
            }else{
                if(noteTitle.isNotEmpty() && noteDesc.isNotEmpty()){
                    val simple_Time_And_Date_Formate = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate : String = simple_Time_And_Date_Formate.format(Date())
                    noteViewModel.addNote(NoteModel(noteTitle,noteDesc,currentDate))
                    Toast.makeText(this,"Note Added Successfully ! ", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    this.finish()
                }
            }
        }

        noteTitle.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        })
        noteDescription.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        })
    }
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (view != null && view is EditText) {
                val r = Rect()
                view.getGlobalVisibleRect(r)
                val rawX = ev.rawX.toInt()
                val rawY = ev.rawY.toInt()
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

