package com.shubham.notes.UI.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.shubham.notes.R
import androidx.activity.viewModels
import com.shubham.notes.UI.activities.dao.NotesViewModel
import com.shubham.notes.UI.activities.dao.NotesViewModelFactory
import com.shubham.notes.UI.activities.entity.Notes
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    var noteContentEditText : EditText? = null;
    var noteTitleEditText : EditText? = null;
    var createdTimestamp : Long = 0;
    var id:Long = 0L;

    private val notesViewModel: NotesViewModel by viewModels {

        NotesViewModelFactory((application as NotesApplication).repository)
        // in turn calls
        //  WordRepository(database.wordDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)


        Log.d("notesViewModel Home", "notesViewModel Home"+notesViewModel)
        var buttonListNotes : ImageButton = findViewById<ImageButton>(R.id.nav_button) as ImageButton
        var deleteButton : ImageButton = findViewById<ImageButton>(R.id.delete_button) as ImageButton

        buttonListNotes.setOnClickListener(this)
        deleteButton.setOnClickListener(this)

        noteContentEditText = findViewById<EditText>(R.id.note_content) as EditText
        noteTitleEditText = findViewById<EditText>(R.id.note_title) as EditText
//        notesViewModel.allWords.observe(this@HomeActivity) { notes ->
//            Log.d("Note Created","Note Created")
//            Toast.makeText(this,"Note Created",Toast.LENGTH_LONG).show()
//
//        }

        val extras: Bundle? = getIntent().getExtras();

        if (intent.hasExtra("id")) {
            id=extras!!.getLong("id")
            val currentNote :Notes =notesViewModel.getNote(id);

            noteTitleEditText!!.setText(currentNote.title)
            noteContentEditText!!.setText(currentNote.note)
            createdTimestamp=currentNote.creationTime
        }

        noteTitleEditText!!.addTextChangedListener(object :TextWatcher {

            override fun afterTextChanged(s: Editable) {
//                Log.d("afterTextChanged","afterTextChanged")
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
//                Log.d("beforeTextChanged","beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                Log.d("onTextChanged","onTextChanged")
                autoSave()
            }
        })
        noteContentEditText!!.addTextChangedListener(object :TextWatcher {

            override fun afterTextChanged(s: Editable) {
//                Log.d("afterTextChanged","afterTextChanged")
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
//                Log.d("beforeTextChanged","beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
//                Log.d("onTextChanged","onTextChanged")
                autoSave()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.nav_button -> navButton()
            R.id.delete_button ->  deleteButton()
        }
    }

    private fun deleteButton() {
        notesViewModel.delete(id)
        navButton()
    }

    private fun navButton(){
        finish()
    }

    fun autoSave(){
        var noteContent : String =noteContentEditText!!.text.toString() ;
        var noteTitle : String =noteTitleEditText!!.text.toString() ;
        if(noteContent.isEmpty() && noteTitle.isEmpty())
        {
            return
        }
        if(id==0L) {
            createdTimestamp=System.currentTimeMillis()
            id =notesViewModel.insert(Notes(noteTitle, noteContent,createdTimestamp, createdTimestamp))
        }
        else {
            notesViewModel.update(Notes(noteTitle, noteContent, createdTimestamp, System.currentTimeMillis(), id))
        }
    }
}