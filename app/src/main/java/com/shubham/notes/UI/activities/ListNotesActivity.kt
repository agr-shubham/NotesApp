package com.shubham.notes.UI.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.shubham.notes.R
import com.shubham.notes.UI.activities.adapters.ListNotesAdapter
import com.shubham.notes.UI.activities.dao.NotesViewModel
import com.shubham.notes.UI.activities.dao.NotesViewModelFactory
import com.shubham.notes.UI.activities.entity.Notes
import com.shubham.notes.UI.activities.fragments.NoNotesCreatedFragment
import com.shubham.notes.UI.activities.fragments.NotesListFragment
import com.shubham.notes.databinding.ActivityListNotesBinding

class ListNotesActivity : AppCompatActivity(){

    private lateinit var actionBarToggle: ActionBarDrawerToggle

    private lateinit var binding : ActivityListNotesBinding

    private val notesViewModel: NotesViewModel by viewModels {

        NotesViewModelFactory((application as NotesApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //view binding
        binding = ActivityListNotesBinding.inflate(layoutInflater)
        setContentView( binding.root)

        val noNotesCreatedFragment = NoNotesCreatedFragment()
        val notesListFragment = NotesListFragment()
        notesViewModel.allNotes.observe(this@ListNotesActivity) { notes ->
            notes?.let {
                notesListFragment.updateAdapterList(it)
            }

            //For 0 notes present
            if (notesViewModel.allNotes.value!!.isEmpty()) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame, noNotesCreatedFragment)
                transaction.commit()
            }
            else {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame, notesListFragment)
                transaction.commit()
            }
        }


        actionBarToggle = ActionBarDrawerToggle(this, binding.drawerLayout, 0, 0)
        binding.drawerLayout.addDrawerListener(actionBarToggle)


        // Display the hamburger icon to launch the drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Call syncState() on the action bar so it'll automatically change to the back button when the drawer layout is open
        actionBarToggle.syncState()

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.about -> {
                    Log.d("about","about")
                    Toast.makeText(this,"about",Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, AboutPageActivity::class.java)
//                    startActivity(intent)
                    true
                }
                R.id.contactus -> {
                    Log.d("about","contactus")
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:9183281237"))
                    startActivity(intent)
                    true
                }
                else -> {
                    Log.d("about","else")
                    false
                }
            }
        }
    }
    fun deleteButton(note : Notes) {
        notesViewModel.delete(note.id)
    }

    override fun onSupportNavigateUp(): Boolean {
        binding.drawerLayout.openDrawer(binding.navView)
        return true
    }

    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list_notes_page,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarToggle.onOptionsItemSelected(item)){
            return true
        }
        when(item.itemId)
        {
            R.id.new_note -> addNewNote()

        }
        return super.onOptionsItemSelected(item)
    }

    private fun addNewNote() {
        val newNoteIntent = Intent(this, HomeActivity::class.java)
        startActivity(newNoteIntent)
    }

}