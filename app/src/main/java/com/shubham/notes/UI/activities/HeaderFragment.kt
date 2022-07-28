package com.shubham.notes.UI.activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.shubham.notes.R
import com.shubham.notes.UI.activities.Notes

class HeaderFragment : Fragment() ,View.OnClickListener{
    private var containerP: ViewGroup? =null;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        containerP=container
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_header, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var buttonListNotes : ImageButton = view.findViewById<ImageButton>(R.id.nav_button) as ImageButton
        var saveButton : ImageButton = view.findViewById<ImageButton>(R.id.save_button) as ImageButton

        buttonListNotes.setOnClickListener(this)
        saveButton.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.nav_button -> navButton()
            R.id.save_button ->  saveButton()
        }
    }

    private fun navButton(){
        Log.d("container",containerP.toString())
        var intentListNotes = Intent(containerP!!.context,ListNotesActivity::class.java)
        startActivity(intentListNotes,ActivityOptions.makeSceneTransitionAnimation(containerP!!.context as Activity?).toBundle())
    }

    fun saveButton(){
        Log.d("Entered saveButton","Entered save button")
        var notesDbUtility: NotesDbUtility? = null
        notesDbUtility = NotesDbUtility(containerP!!.context as Activity)
        notesDbUtility!!.open()
//        var noteContent : String = noteContentEditText.text.toString();
        var noteContent : String = "sad faw\n fasfdasdfsdsdf";
        var titleLastIndex :Int = noteContent.indexOf('\n');
        if(noteContent.trim().length==0)
        {
            //Toast empty note
        }
        if(titleLastIndex==-1)
        {
            titleLastIndex= Integer.min(40, noteContent.length);
        }
        else
        {
            titleLastIndex= Integer.min(titleLastIndex, 40);
        }
        var noteTitle = noteContent.substring(0,titleLastIndex)
        notesDbUtility.addNote(Notes(0,noteTitle,noteContent))
    }

}