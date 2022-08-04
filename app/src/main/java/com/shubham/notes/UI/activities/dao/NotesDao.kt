package com.shubham.notes.UI.activities.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shubham.notes.UI.activities.entity.Notes

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes_table ORDER BY update_time DESC")
    fun getAlphabetizedNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(word: Notes): Long

//    @Query("DELETE FROM notes_table")
//    suspend fun deleteAll()

    @Update
    abstract fun update(notes: Notes)

    @Query("SELECT * FROM notes_table WHERE id= :id")
    abstract fun getNote(id: Long) : Notes

    @Query("DELETE FROM notes_table WHERE id= :id")
    abstract fun deleteNote(id: Long)

}