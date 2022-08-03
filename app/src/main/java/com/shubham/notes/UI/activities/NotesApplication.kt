package com.shubham.notes.UI.activities

import android.app.Application
import com.shubham.notes.UI.activities.dao.NotesRepository
import com.shubham.notes.UI.activities.dao.NotesRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NotesApplication : Application() {

    private val applicationScope = CoroutineScope( SupervisorJob())

    private val database   by lazy { NotesRoomDatabase.getDatabase(this,applicationScope ) }
    val repository by lazy { NotesRepository(database.notesDao()) }

}