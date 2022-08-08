package com.shubham.notes.UI.activities.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.shubham.notes.R
import com.shubham.notes.UI.activities.MainActivity
import com.shubham.notes.UI.activities.entity.Notes
import com.shubham.notes.databinding.FragmentEditNoteBinding
import com.shubham.notes.databinding.FragmentNotesListBinding

class EditNoteFragment(var id:Long = 0L) : Fragment() {

    private lateinit var binding : FragmentEditNoteBinding
    var currentNote:Notes?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentEditNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(id!=0L) {
            currentNote = (activity as MainActivity).getNote(id)

            binding.noteTitle.setText(currentNote!!.title)
            binding.noteContent.setText(currentNote!!.note)

        }
        binding.noteTitle.addTextChangedListener(object : TextWatcher {

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
        binding.noteContent.addTextChangedListener(object : TextWatcher {

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_note_page,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.delete_note -> deleteButton()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteButton() {
        (activity as MainActivity).deleteButton(currentNote!!)
    }

    fun autoSave(){
        val noteContent : String =binding.noteContent.text.toString()
        val noteTitle : String =binding.noteTitle.text.toString()
        if(noteContent.isEmpty() && noteTitle.isEmpty())
        {
            return
        }
        if(id==0L) {
            val createdTimestamp:Long=System.currentTimeMillis()

            id =(activity as MainActivity).insertNote(Notes(noteTitle, noteContent,createdTimestamp, createdTimestamp))
            currentNote=(activity as MainActivity).getNote(id)
        }
        else {
            (activity as MainActivity).updateNote(Notes(noteTitle, noteContent, currentNote!!.creationTime, System.currentTimeMillis(), id))
        }
    }
}