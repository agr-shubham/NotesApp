package com.shubham.notes.UI.activities.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shubham.notes.UI.activities.entity.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes_table ORDER BY title ASC")
    fun getAlphabetizedNotes(): Flow<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Notes)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAll()

}