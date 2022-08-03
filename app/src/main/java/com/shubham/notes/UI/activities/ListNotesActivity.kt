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
import com.shubham.notes.databinding.ActivityListNotesBinding
import com.shubham.notes.databinding.HomeBinding

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

        binding.recyclerView1.addItemDecoration(DividerItemDecoration(this, 0));
        binding.recyclerView1.layoutManager = LinearLayoutManager(this);//,LinearLayout.VERTICAL,false)
        binding.recyclerView1.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val adapter =  ListNotesAdapter();

        binding.recyclerView1.adapter = adapter

        binding.recyclerView1.setHasFixedSize(true)
        notesViewModel.allWords.observe(this@ListNotesActivity) { notes ->
            notes?.let {
                adapter.updateList(it)
            }

            //For 0 notes present
            if (notesViewModel.allWords.value!!.isEmpty()) {
                binding.recyclerView1.visibility = View.GONE;
                binding.noNotesMessage.visibility = View.VISIBLE;
            }
            else {
                binding.recyclerView1.visibility = View.VISIBLE;
                binding.noNotesMessage.visibility = View.GONE;
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