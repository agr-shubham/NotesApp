package com.shubham.notes.UI.activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.shubham.notes.R

class HeaderFragmentListPage : Fragment() {
    private var containerP: ViewGroup? =null;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        containerP=container
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_header, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var buttonListNotes : ImageButton = view.findViewById<ImageButton>(R.id.nav_button) as ImageButton

        buttonListNotes.setOnClickListener {
            (containerP!!.context as Activity).finishAfterTransition()
        }
    }


}