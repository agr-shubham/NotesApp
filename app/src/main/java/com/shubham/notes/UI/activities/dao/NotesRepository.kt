package com.shubham.notes.UI.activities.dao

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.shubham.notes.UI.activities.entity.Notes

class NotesRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<Notes>> = notesDao.getAlphabetizedNotes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(note: Notes) : Long {
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