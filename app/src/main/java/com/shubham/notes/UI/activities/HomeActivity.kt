package com.shubham.notes.UI.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.shubham.notes.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        var header = HeaderFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.header, header)
            commit()
        }

    }

}