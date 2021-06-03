package com.scollon.simon

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteStatement




class DatabaseHandler3(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "3x3Database2"

        private val TABLE_CONTACTS = "blocksTable2"

        private val KEY_ID = "_id"
        private val KEY_SCORE = "score"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SCORE + " INT"
                + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }


    fun addEmployee(emp: RecordModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_SCORE, emp.score) // EmpModelClass Name

        // Inserting employee details using insert query.
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    //Method to read the records from database in form of ArrayList
    fun viewEmployee(): ArrayList<RecordModel> {

        val empList: ArrayList<RecordModel> = ArrayList<RecordModel>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM ${TABLE_CONTACTS}"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var score: Int
        var email: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                score = cursor.getInt(cursor.getColumnIndex(KEY_SCORE))

                val emp = RecordModel(id = id, score = score)
                empList.add(emp)

            } while (cursor.moveToNext())
        }
        return empList
    }

    fun updateEmployee(emp: RecordModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_SCORE, emp.score) // EmpModelClass Name

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues, KEY_ID + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }
    fun deleteEmployee(emp: RecordModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id) // EmpModelClass id
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS, KEY_ID + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }


    fun getOne(id: Int): RecordModel? {
        val db = this.writableDatabase
        val query = arrayOf<String>(KEY_ID, KEY_SCORE)
        val cursor = db.query(
            TABLE_CONTACTS,
            query,
            KEY_ID.toString() + "=?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        return RecordModel(
            cursor!!.getInt(0),
            cursor.getInt(1)
        )
    }

    fun getBmiCount(): Int {
        val db = this.readableDatabase
        val count = DatabaseUtils.queryNumEntries(db, TABLE_CONTACTS)
        db.close()
        var countInt = count.toInt()
        return countInt
    }

    fun getBiggestInTheColumn(): Int {

        val db = this.writableDatabase
        return DatabaseUtils.longForQuery(db, "SELECT MAX(score) FROM $TABLE_CONTACTS", null)
            .toInt()
    }






}