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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_notes_page,menu)

        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)

        val searchView: SearchView = searchItem.getActionView() as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })
    }

    private fun filter(text: String) {
        val filteredlist: ArrayList<Notes> = ArrayList()

        for (item in notes) {
            if (item.title.contains(text,ignoreCase = true) ||
                item.note.contains(text,ignoreCase = true)) {
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