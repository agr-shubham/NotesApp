package com.shubham.notes.UI.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.shubham.notes.R
import androidx.activity.viewModels
import com.shubham.notes.UI.activities.dao.NotesViewModel
import com.shubham.notes.UI.activities.dao.NotesViewModelFactory
import com.shubham.notes.UI.activities.entity.Notes
import com.shubham.notes.databinding.HomeBinding

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : HomeBinding

    private var createdTimestamp : Long = 0;
    var id:Long = 0L;

    private val notesViewModel: NotesViewModel by viewModels {

        NotesViewModelFactory((application as NotesApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //View Binding
        binding = HomeBinding.inflate(layoutInflater)
        setContentView( binding.root)

        binding.backButton.setOnClickListener(this)
        binding.deleteButton.setOnClickListener(this)


        val extras: Bundle? = intent.extras;

        if (intent.hasExtra("id")) {
            id=extras!!.getLong("id")
            val currentNote :Notes =notesViewModel.getNote(id);

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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.back_button -> backButton()
            R.id.delete_button ->  deleteButton()
        }
    }

    private fun deleteButton() {
        notesViewModel.delete(id)
        backButton()
    }

    private fun backButton(){
        finish()
    }

    fun autoSave(){
        var noteContent : String =binding.noteContent!!.text.toString() ;
        var noteTitle : String =binding.noteTitle!!.text.toString() ;
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