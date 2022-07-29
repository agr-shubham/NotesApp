package com.shubham.notes.UI.activities.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shubham.notes.UI.activities.entity.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes_table ORDER BY title ASC")
    fun getAlphabetizedNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Notes)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAll()

    @Update
    abstract fun update(notes: Notes)

    @Query("SELECT * FROM notes_table WHERE id= :id")
    abstract fun getNote(id: Long) : Notes

}