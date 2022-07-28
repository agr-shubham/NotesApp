package com.shubham.notes.UI.activities

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.shubham.notes.UI.activities.Notes

class NotesDbUtility(mainActivity: Activity) {
    var context :      Context
    //var mainActivity : MainActivity

    private  var databaseUtility : NotesDatabaseUtility? = null
    private  var sqliteDatabase: SQLiteDatabase? = null ;

    fun open() {
        sqliteDatabase = databaseUtility!!.writableDatabase
    }



    // House keeping work
    val handler = Handler()


    // Core data base
    // How to create a frame work around this
    // passing this ( MainActivity context is wrong )
    // so use Application context since its a single obj..

    private  inner  class NotesDatabaseUtility( context: Context) :
        SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION){
        override fun onCreate(sqliteDatabase : SQLiteDatabase?) {
            sqliteDatabase!!.execSQL( TABLE_CREATE_QUERY)
        }

        override fun onUpgrade(sqliteDatabase : SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

            Log.d(
                "Contentdatabase",
                "Upgrading database from version "
                        + oldVersion + " to " + newVersion
                        + ", which will destroy  old data"
            )


            sqliteDatabase!!.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME)

            onCreate(sqliteDatabase)
        }

    }

    // CRUD operations



    fun addNote(note: Notes) {
        Log.d("addNote","Inside function addNote")
        val values = ContentValues()

        values.put(COLUMN_TITLE,note.title)
        values.put(COLUMN_NOTE,note.note)

        val id =  sqliteDatabase!!.insert(TABLE_NAME,null,values)

        if( id!= -1L){

            // outdated!!! Coroutine here..
            handler.post {
                Runnable {
                    Toast.makeText(context,"Insert records Success ", Toast.LENGTH_SHORT).show()
                }
            }


        }

    }
    //////////////////////////
    fun listStudentByToast() {


        val cursor = sqliteDatabase!!.query(TABLE_NAME,null,null,null,
            null,null,null)


        if( cursor != null && cursor.moveToFirst()){

            do{
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                val age = cursor.getString(cursor.getColumnIndex(COLUMN_NOTE))

                // take the data name and age on to Student POJO class
                //                 pass the same on to the recycler view..
                Toast.makeText(context, " Name : $name Age : $age", Toast.LENGTH_SHORT).show()



            } while ( cursor.moveToNext())

            if( cursor != null){

                cursor.close()
            }

        }

    }
    //////////////////
    fun updateStudent(note: Notes) {

        val values = ContentValues()

        values.put(COLUMN_NOTE,note.note)
        values.put(COLUMN_TITLE,note.title)
        val numOfRowsUpdated =
            sqliteDatabase!!.update(TABLE_NAME,values, COLUMN_ID +"=?",
                arrayOf(note.id.toString()))
        Toast.makeText(context, " Number of rec updated $numOfRowsUpdated", Toast.LENGTH_SHORT).show()



    }

    fun deleteAStudent(note: Notes) {

        val numOfRowsUpdated =
            sqliteDatabase!!.delete(TABLE_NAME, COLUMN_ID +"=?",
                arrayOf(note.id.toString()))
        Toast.makeText(context, " Number of rec Deleted $numOfRowsUpdated", Toast.LENGTH_SHORT).show()


    }

    fun DeleteAllRecords(){
        sqliteDatabase!!.delete(TABLE_NAME,null,null)
        Toast.makeText(context, "  Deleted all the records ", Toast.LENGTH_SHORT).show()

    }
    ////////////////////////////////////////////////


    // sub set of object to deal with the db relevent declarations
    companion object{

        //1 Db base

        val DB_NAME = "notes_app.db"
        //2 table
        val TABLE_NAME = "notes"

        // 3 deckare the foelds table

        val COLUMN_ID = "id"
        val COLUMN_TITLE = "title"
        val COLUMN_NOTE = "note"

        // 4 version

        val DB_VERSION = 1


        // creating fields for a table Employee


        // >>

        //                  "ps_employee.db"

        // Pri_key                Name                 Age         Address
        //  1                     Atif                 23
        //  2                     Aameer               24
        //  3                     Rushik               22



        // SQlite statements are more like English

        // "CREATE TABLE employee ( _id integer primary key autoincrement, name text, age text );";

        val TABLE_CREATE_QUERY =
            ("create table " + TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement,"
                    + COLUMN_TITLE + " text," +
                    COLUMN_NOTE + " text);")
    }

    init {
        // all the db thread init will take place

        context           = mainActivity
        // this.mainActivity = mainActivity

        // Phase II
        databaseUtility = NotesDatabaseUtility(context)




    }
}