package com.shubham.notes.UI.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.shubham.notes.R
import java.lang.Integer.max
import java.lang.Integer.min

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        var header = HeaderFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.header, header)
            commit()
        }
        var noteContentEditText : EditText = findViewById<EditText>(R.id.note_content) as EditText


    }

}