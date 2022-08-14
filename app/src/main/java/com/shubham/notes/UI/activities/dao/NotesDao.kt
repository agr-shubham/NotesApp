package com.shubham.notes.UI.activities.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shubham.notes.UI.activities.entity.Notes

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes_table ORDER BY update_time DESC")
    fun getAlphabetizedNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Notes): Long

    @Update
    fun update(notes: Notes)

    @Query("SELECT * FROM notes_table WHERE id= :id")
    fun getNote(id: Long) : Notes

    @Query("DELETE FROM notes_table WHERE id= :id")
    fun deleteNote(id: Long)

}