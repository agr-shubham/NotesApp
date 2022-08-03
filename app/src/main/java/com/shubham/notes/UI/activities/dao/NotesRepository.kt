package com.shubham.notes.UI.activities.dao

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.shubham.notes.UI.activities.entity.Notes

class NotesRepository(private val notesDao: NotesDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allNotes: LiveData<List<Notes>> = notesDao.getAlphabetizedNotes() as LiveData<List<Notes>>

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(note: Notes) : Long {
        // This is where the user typed word gets inserted to the DB..
        return notesDao.insert(note)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun update(notes: Notes) {
        notesDao.update(notes)

    }

    fun getNote(id: Long) :Notes {
        return notesDao.getNote(id)
    }

    fun delete(id: Long) {
        return notesDao.deleteNote(id)
    }
}