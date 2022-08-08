package com.shubham.notes.UI.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.shubham.notes.R
import com.shubham.notes.UI.activities.dao.NotesViewModel
import com.shubham.notes.UI.activities.dao.NotesViewModelFactory
import com.shubham.notes.UI.activities.entity.Notes
import com.shubham.notes.UI.activities.fragments.EditNoteFragment
import com.shubham.notes.UI.activities.fragments.NoNotesCreatedFragment
import com.shubham.notes.UI.activities.fragments.NotesListFragment
import com.shubham.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private val notesViewModel: NotesViewModel by viewModels {

        NotesViewModelFactory((application as NotesApplication).repository)
    }

    private lateinit var actionBarToggle: ActionBarDrawerToggle

    private lateinit var binding : ActivityMainBinding

    lateinit var notesListFragment : NotesListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView( binding.root)

        actionBarToggle = ActionBarDrawerToggle(this, binding.drawerLayout, 0, 0)
        binding.drawerLayout.addDrawerListener(actionBarToggle)

        // Display the hamburger icon to launch the drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Call syncState() on the action bar so it'll automatically change to the back button when the drawer layout is open
        actionBarToggle.syncState()

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.about -> {
                    val intent = Intent(this,AboutUsActivity::class.java)
                    startActivity(intent)
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
//        val noNotesCreatedFragment = NoNotesCreatedFragment()
        notesListFragment = NotesListFragment()
        notesViewModel.allNotes.observe(this@MainActivity) { notes ->
            notes?.let {
                notesListFragment.updateAdapterList(it)
            }
        }

        loadListNotesFragment()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadListNotesFragment(){
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, notesListFragment)
        transaction.commit()
    }

    fun loadEditNoteFragment(id: Long) {
        val editNoteFragment = EditNoteFragment(id)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,editNoteFragment )
        transaction.addToBackStack("editnote")
        transaction.commit()
    }

    fun deleteButton(note : Notes) :Boolean {
        val alert= AlertDialog.Builder(this)
        var flag:Boolean=false;
        alert.setTitle("Delete entry")
        alert.setMessage("Are you sure you want to delete?")
        alert.setPositiveButton(android.R.string.yes, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                flag=true
                notesViewModel.delete(note.id)
            }
        })
        alert.setNegativeButton(android.R.string.no,
            DialogInterface.OnClickListener { dialog, which -> // close dialog
                dialog.cancel()
            })
        alert.show()
        return flag
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