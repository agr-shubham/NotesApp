package com.shubham.notes.UI.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.shubham.notes.R
import com.shubham.notes.UI.activities.dao.NotesViewModel
import com.shubham.notes.UI.activities.dao.NotesViewModelFactory
import com.shubham.notes.UI.activities.entity.Notes
import com.shubham.notes.databinding.HomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : HomeBinding

    private var createdTimestamp : Long = 0
    var id:Long = 0L

    private val notesViewModel: NotesViewModel by viewModels {

        NotesViewModelFactory((application as NotesApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //View Binding
        binding = HomeBinding.inflate(layoutInflater)
        setContentView( binding.root)

        val extras: Bundle? = intent.extras

        if (intent.hasExtra("id")) {
            id=extras!!.getLong("id")
            val currentNote :Notes =notesViewModel.getNote(id)

            binding.noteTitle.setText(currentNote.title)
            binding.noteContent.setText(currentNote.note)
            createdTimestamp=currentNote.creationTime
        }

        binding.noteTitle.addTextChangedListener(object :TextWatcher {

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
        binding.noteContent.addTextChangedListener(object :TextWatcher {

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

    private fun deleteButton() {
        notesViewModel.delete(id)
        backButton()
    }

    private fun backButton(){
        finish()
    }

    fun autoSave(){
        val noteContent : String =binding.noteContent.text.toString()
        val noteTitle : String =binding.noteTitle.text.toString()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit_note_page,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            android.R.id.home -> backButton()
            R.id.delete_note -> deleteButton()

        }
        return super.onOptionsItemSelected(item)
    }
}