package com.muiedhossain.notepad_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.muiedhossain.notepad_kotlin.Activity.InsertUpdateNoteActivity
import com.muiedhossain.notepad_kotlin.Adapter.NoteClickDeleteInterface
import com.muiedhossain.notepad_kotlin.Adapter.NoteClickedInterface
import com.muiedhossain.notepad_kotlin.Adapter.RVAdapter
import com.muiedhossain.notepad_kotlin.Model.NoteModel
import com.muiedhossain.notepad_kotlin.ViewModel.NoteViewModel

//interface gular implementation korte hoise(Note clicked interface & note click delete interface)
class MainActivity : AppCompatActivity(), NoteClickedInterface, NoteClickDeleteInterface {

    lateinit var notesRV : RecyclerView
    lateinit var addNoteBtn : FloatingActionButton
    lateinit var noteViewModel : NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notesRV = findViewById(R.id.itemRecyclerView)
        addNoteBtn = findViewById(R.id.addNoteBtn)

        notesRV.layoutManager = GridLayoutManager(this,2)

        //jehetu 2 ta interface adapter e ase tai oisob gular methode eikhane implement korte hobe
        val noteRvAdapter = RVAdapter(this,this,this)
        notesRV.adapter = noteRvAdapter
        noteViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, Observer { list ->
            list ?. let {
                noteRvAdapter.updateList(it)
            }
        })

        addNoteBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, InsertUpdateNoteActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onNoteClicked(note: NoteModel) {
        val intent = Intent(this@MainActivity, InsertUpdateNoteActivity::class.java)
        intent.putExtra("noteType","Edit")
        intent.putExtra("noteTitle",note.noteTitle)
        intent.putExtra("noteDescription",note.noteDescription)
        intent.putExtra("noteID",note.id)
        startActivity(intent)
    }

    override fun onDeleteIconClicked(note: NoteModel) {
        noteViewModel.deleteNote(note)
        Toast.makeText(this,"${note.noteTitle} Deleted",Toast.LENGTH_SHORT).show()
    }
}