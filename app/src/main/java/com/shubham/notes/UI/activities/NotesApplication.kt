package com.shubham.notes.UI.activities

import android.app.Application
import com.shubham.notes.UI.activities.dao.NotesRepository
import com.shubham.notes.UI.activities.dao.NotesRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NotesApplication : Application() {

// Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts

    val applicationScope = CoroutineScope( SupervisorJob())
//val applicationScope = CoroutineScope(CoroutineName("Sathya")+ SupervisorJob()+Dispatchers.IO)

    val database   by lazy { NotesRoomDatabase.getDatabase(this,applicationScope ) }
    val repository by lazy { NotesRepository(database.notesDao()) }

}