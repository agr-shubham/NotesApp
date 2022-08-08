package com.shubham.notes.UI.activities.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubham.notes.R
import com.shubham.notes.UI.activities.MainActivity
import com.shubham.notes.UI.activities.NotesApplication
import com.shubham.notes.UI.activities.adapters.ListNotesAdapter
import com.shubham.notes.UI.activities.dao.NotesViewModel
import com.shubham.notes.UI.activities.dao.NotesViewModelFactory
import com.shubham.notes.UI.activities.entity.Notes
import com.shubham.notes.databinding.FragmentNotesListBinding


class NotesListFragment : Fragment() {


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

    }

    fun updateAdapterList(notes:List<Notes>){
        adapter.updateList(notes)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_notes_page,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.new_note -> (activity as MainActivity).addNewNote()
        }
        return super.onOptionsItemSelected(item)
    }



}