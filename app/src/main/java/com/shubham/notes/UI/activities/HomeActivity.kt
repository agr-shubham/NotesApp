package com.shubham.notes.UI.activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.shubham.notes.R
import androidx.activity.viewModels
import com.shubham.notes.UI.activities.dao.NotesRepository
import com.shubham.notes.UI.activities.dao.NotesViewModel
import com.shubham.notes.UI.activities.dao.NotesViewModelFactory
import com.shubham.notes.UI.activities.entity.Notes

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    var noteContentEditText : EditText? = null;

    private val notesViewModel: NotesViewModel by viewModels {

        NotesViewModelFactory((application as NotesApplication).repository)
        // in turn calls
        //  WordRepository(database.wordDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        var buttonListNotes : ImageButton = findViewById<ImageButton>(R.id.nav_button) as ImageButton
        var saveButton : ImageButton = findViewById<ImageButton>(R.id.save_button) as ImageButton

        buttonListNotes.setOnClickListener(this)
        saveButton.setOnClickListener(this)

        noteContentEditText = findViewById<EditText>(R.id.note_content) as EditText
        notesViewModel.allWords.observe(this@HomeActivity) { notes ->

        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.nav_button -> navButton()
            R.id.save_button ->  saveButton()
        }
    }

    private fun navButton(){
        var intentListNotes = Intent(this,ListNotesActivity::class.java)
        startActivity(intentListNotes,
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    fun saveButton(){
        Log.d("Entered saveButton","Entered save button")

//        var noteContent : String = noteContentEditText.text.toString();
        var noteContent : String =noteContentEditText!!.text.toString() ;
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
        notesViewModel.insert(Notes(noteTitle,noteContent,0,0))
    }
}