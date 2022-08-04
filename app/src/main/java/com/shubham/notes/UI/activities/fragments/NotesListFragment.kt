package com.shubham.notes.UI.activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubham.notes.R
import com.shubham.notes.UI.activities.adapters.ListNotesAdapter
import com.shubham.notes.UI.activities.entity.Notes
import com.shubham.notes.databinding.FragmentNotesListBinding


class NotesListFragment : Fragment() {

    val adapter =  ListNotesAdapter();
    private lateinit var binding : FragmentNotesListBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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



}