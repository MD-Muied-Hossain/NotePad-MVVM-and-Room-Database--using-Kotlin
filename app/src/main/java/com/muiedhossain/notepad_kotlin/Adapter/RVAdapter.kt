package com.muiedhossain.notepad_kotlin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muiedhossain.notepad_kotlin.Model.NoteModel
import com.muiedhossain.notepad_kotlin.R

class RVAdapter(
     val context : Context
    ,val noteClickDeleteInterface : NoteClickDeleteInterface
    ,val noteClickedInterface : NoteClickedInterface ): RecyclerView.Adapter<RVAdapter.ViewHolder>() {

        private val allNoteList = ArrayList<NoteModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTv.setText(allNoteList.get(position).noteTitle)
        holder.timeTv.setText("Last Updated : "+allNoteList.get(position).timeStamp)

        holder.deleteIcon.setOnClickListener{
            noteClickDeleteInterface.onDeleteIconClicked(allNoteList.get(position))
        }
        holder.itemView.setOnClickListener{
            noteClickedInterface.onNoteClicked(allNoteList.get(position))
        }
    }

    override fun getItemCount(): Int {
        return allNoteList.size
    }

    /////////to update our List
    fun updateList(newList : List<NoteModel>){
        allNoteList.clear()
        allNoteList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val noteTv = itemView.findViewById<TextView>(R.id.tvNoteTitle)
        val timeTv = itemView.findViewById<TextView>(R.id.tvTimeStamp)
        val deleteIcon = itemView.findViewById<ImageView>(R.id.deleteBtn)
    }
}

//when the delete & another item is been clicked then the interface is needed
interface NoteClickDeleteInterface {
    fun onDeleteIconClicked(note : NoteModel)
}
interface NoteClickedInterface {
    fun onNoteClicked(note : NoteModel)
}