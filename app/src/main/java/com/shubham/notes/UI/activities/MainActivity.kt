package com.shubham.notes.UI.activities

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.shubham.notes.R
import com.shubham.notes.UI.activities.dao.NotesViewModel
import com.shubham.notes.UI.activities.dao.NotesViewModelFactory
import com.shubham.notes.UI.activities.entity.Notes
import com.shubham.notes.UI.activities.fragments.NotesListFragmentDirections
import com.shubham.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    val notesViewModel: NotesViewModel by viewModels {

        NotesViewModelFactory((application as NotesApplication).repository)
    }

    private lateinit var navController: NavController

    private lateinit var actionBarToggle: ActionBarDrawerToggle

    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView( binding.root)

        actionBarToggle = ActionBarDrawerToggle(this, binding.drawerLayout, 0, 0)
        binding.drawerLayout.addDrawerListener(actionBarToggle)

        val navHostFragment=supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController=navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun loadEditNoteFragment(id: Long) {
        val action=NotesListFragmentDirections.actionNotesListFragmentToEditNoteFragment2(id)
        navController.navigate(action)
    }

    private fun loadAboutUsFragment(){
        navController.navigate(R.id.action_notesListFragment_to_aboutUsFragment)
    }

    fun deleteButton(note : Notes) {
        val alert= AlertDialog.Builder(this)
        alert.setTitle("Delete entry")
        alert.setMessage("Are you sure you want to delete?")
        alert.setPositiveButton(android.R.string.yes, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                notesViewModel.delete(note.id)
            }
        })
        alert.setNegativeButton(android.R.string.no,
            DialogInterface.OnClickListener { dialog, which -> // close dialog
                dialog.cancel()
            })
        alert.show()
    }

    fun addNewNote() {
        loadEditNoteFragment(0)
    }

    fun getNote(id:Long): Notes {
        return notesViewModel.getNote(id)
    }

    fun insertNote(note:Notes):Long{
        return notesViewModel.insert(note)
    }

    fun updateNote(note:Notes){
        notesViewModel.update(note)
    }

}