package com.shubham.notes.UI.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
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

    private lateinit var appBarConfiguration : AppBarConfiguration

    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView( binding.root)

        val navHostFragment=supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController=navHostFragment.navController
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.aboutUsFragment -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START, false);
                    navController.navigate(R.id.aboutUsFragment)
                    true
                }
                R.id.contactus -> {
                    Log.d("about","contactus")
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:9183281237"))
                    startActivity(intent)
                    true
                }
                else -> {
                    false}
            }
        }
        appBarConfiguration= AppBarConfiguration(navController.graph,binding.drawerLayout)

        setupActionBarWithNavController(navController,appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return true
        }
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun loadEditNoteFragment(id: Long) {
        val action=NotesListFragmentDirections.actionNotesListFragmentToEditNoteFragment2(id)
        navController.navigate(action)
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