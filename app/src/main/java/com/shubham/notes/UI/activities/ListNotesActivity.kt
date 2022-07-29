package com.shubham.notes.UI.activities

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
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


        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView1) as RecyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(this, 0));
//        val notesListLD: LiveData<List<Notes>> = (application as NotesApplication).repository.allWords
        val notesList = listOf<Notes>()



        var buttonListNotes : ImageButton = findViewById<ImageButton>(R.id.nav_button) as ImageButton

        buttonListNotes.setOnClickListener {
            (this as Activity).finishAfterTransition()
        }

        recyclerView.layoutManager = LinearLayoutManager(this);//,LinearLayout.VERTICAL,false)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val adapter =  ListNotesAdapter();

        recyclerView.adapter = adapter

        recyclerView.setHasFixedSize(true)
        notesViewModel.allWords.observe(this@ListNotesActivity) { notes ->
            notes?.let {
                adapter.updateList(it)
            }
        }
        notesList.forEach {
            notesViewModel.insert(it)
        }
    }


}