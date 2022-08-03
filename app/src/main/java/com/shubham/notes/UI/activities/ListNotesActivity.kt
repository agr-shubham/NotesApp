package com.shubham.notes.UI.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shubham.notes.R
import com.shubham.notes.UI.activities.adapters.ListNotesAdapter
import com.shubham.notes.UI.activities.dao.NotesViewModel
import com.shubham.notes.UI.activities.dao.NotesViewModelFactory
import com.shubham.notes.UI.activities.entity.Notes

class ListNotesActivity : AppCompatActivity(){

    private val notesViewModel: NotesViewModel by viewModels {

        NotesViewModelFactory((application as NotesApplication).repository)
        // in turn calls
        //  WordRepository(database.wordDao())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_notes)

        Log.d("notesViewModel List", "notesViewModel List"+notesViewModel)

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView1) as RecyclerView
        var noNotesTV= findViewById<TextView>(R.id.no_notes_message) as TextView
        recyclerView.addItemDecoration(DividerItemDecoration(this, 0));
//        val notesListLD: LiveData<List<Notes>> = (application as NotesApplication).repository.allWords
        val notesList = listOf<Notes>()



//        var buttonListNotes : ImageButton = findViewById<ImageButton>(R.id.nav_button) as ImageButton
        var new_note_button : ImageButton = findViewById<ImageButton>(R.id.new_note_button) as ImageButton

//        buttonListNotes.setOnClickListener {
//            (this as Activity).finishAfterTransition()
//        }

        recyclerView.layoutManager = LinearLayoutManager(this);//,LinearLayout.VERTICAL,false)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val adapter =  ListNotesAdapter();

        recyclerView.adapter = adapter

        recyclerView.setHasFixedSize(true)
        notesViewModel.allWords.observe(this@ListNotesActivity) { notes ->
            notes?.let {
                adapter.updateList(it)
            }
            if (notesViewModel.allWords.value!!.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                noNotesTV.setVisibility(View.VISIBLE);
            }
            else {
                recyclerView.setVisibility(View.VISIBLE);
                noNotesTV.setVisibility(View.GONE);
            }
        }

        new_note_button.setOnClickListener{
            val newNoteIntent = Intent(this, HomeActivity::class.java)
            startActivity(newNoteIntent)
        }

        notesList.forEach {
            notesViewModel.insert(it)
        }


    }
    public fun deleteButton(note : Notes) {
        notesViewModel.delete(note.id)
    }


}