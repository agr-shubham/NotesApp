package com.shubham.notes.UI.activities.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shubham.notes.UI.activities.entity.Notes
import kotlinx.coroutines.CoroutineScope

@Database( entities = [Notes::class], version = 1 , exportSchema = false)
abstract class NotesRoomDatabase : RoomDatabase(){
    abstract fun notesDao() : NotesDao

    companion object {

        @Volatile
        private var INSTANCE: NotesRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NotesRoomDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesRoomDatabase::class.java,
                    "notes_database"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance


            }


        }
    }


}