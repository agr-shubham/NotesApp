package com.shubham.notes.UI.activities.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubham.notes.R
import com.shubham.notes.UI.activities.MainActivity
import com.shubham.notes.UI.activities.adapters.ListNotesAdapter
import com.shubham.notes.UI.activities.entity.Notes
import com.shubham.notes.databinding.FragmentNotesListBinding
import java.util.*


class NotesListFragment : Fragment() {

    var notes:ArrayList<Notes> = ArrayList()

    val adapter =  ListNotesAdapter();
    private lateinit var binding : FragmentNotesListBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentNotesListBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView1.addItemDecoration(DividerItemDecoration(activity, 0))
        binding.recyclerView1.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView1.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        binding.recyclerView1.adapter = adapter

        binding.recyclerView1.setHasFixedSize(true)
        (activity as MainActivity).notesViewModel.allNotes.observe(viewLifecycleOwner) { list ->
            notes.clear()

            notes.addAll(list)
            list?.let {
                adapter.updateList(it)
            }
            checkIfNotesPresent()
        }


    }

    private fun checkIfNotesPresent(){
        if (adapter.getNotesSize()==0) {
            binding.recyclerView1.visibility = View.GONE
            binding.noNotesMessage.visibility = View.VISIBLE
        }
        else {
            binding.recyclerView1.visibility = View.VISIBLE
            binding.noNotesMessage.visibility = View.GONE
        }
    }

    fun updateAdapterList(notesArg:List<Notes>){
        this.notes= notesArg as ArrayList<Notes>
        adapter.updateList(notesArg)
        checkIfNotesPresent()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_notes_page,menu)

        // getting search view of our item.
        // below line is to get our menu item.
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)

        // getting search view of our item.
        val searchView: SearchView = searchItem.getActionView() as SearchView

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText)
                return false
            }
        })
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Notes> = ArrayList()

        // running a for loop to compare elements.
        for (item in notes) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.title.contains(text,ignoreCase = true) ||
                item.note.contains(text,ignoreCase = true)) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        adapter.filterList(filteredlist)
        checkIfNotesPresent()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.new_note -> (activity as MainActivity).addNewNote()
        }
        return super.onOptionsItemSelected(item)
    }



}