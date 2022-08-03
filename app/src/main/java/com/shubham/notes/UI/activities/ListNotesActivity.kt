package com.shubham.notes.UI.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubham.notes.UI.activities.adapters.ListNotesAdapter
import com.shubham.notes.UI.activities.dao.NotesViewModel
import com.shubham.notes.UI.activities.dao.NotesViewModelFactory
import com.shubham.notes.UI.activities.entity.Notes
import com.shubham.notes.databinding.ActivityListNotesBinding

class ListNotesActivity : AppCompatActivity(){

    private lateinit var binding : ActivityListNotesBinding

    private val notesViewModel: NotesViewModel by viewModels {

        NotesViewModelFactory((application as NotesApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //view binding
        binding = ActivityListNotesBinding.inflate(layoutInflater)
        setContentView( binding.root)

        binding.recyclerView1.addItemDecoration(DividerItemDecoration(this, 0))
        binding.recyclerView1.layoutManager = LinearLayoutManager(this)
        binding.recyclerView1.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val adapter =  ListNotesAdapter()

        binding.recyclerView1.adapter = adapter

        binding.recyclerView1.setHasFixedSize(true)
        notesViewModel.allNotes.observe(this@ListNotesActivity) { notes ->
            notes?.let {
                adapter.updateList(it)
            }

            //For 0 notes present
            if (notesViewModel.allNotes.value!!.isEmpty()) {
                binding.recyclerView1.visibility = View.GONE
                binding.noNotesMessage.visibility = View.VISIBLE
            }
            else {
                binding.recyclerView1.visibility = View.VISIBLE
                binding.noNotesMessage.visibility = View.GONE
            }
        }

        binding.newNoteButton.setOnClickListener{
            val newNoteIntent = Intent(this, HomeActivity::class.java)
            startActivity(newNoteIntent)
        }



    }
    fun deleteButton(note : Notes) {
        notesViewModel.delete(note.id)
    }


}