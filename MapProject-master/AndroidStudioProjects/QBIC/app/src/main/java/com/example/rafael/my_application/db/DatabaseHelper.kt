package com.example.rafael.my_application.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rafael.my_application.model.User
import java.util.ArrayList
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_USER_TABLE)

    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL(DROP_USER_TABLE)
        onCreate(db)

    }
    fun  getAllUser(): List<User> {
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_PASSWORD)
        val sortOrder = "$COLUMN_USER_NAME ASC"
       // val selectQuery = "SELECT * FROM  $TABLE_USER"
        val userList = ArrayList<User>()
        val db = this.readableDatabase
      val cursor = db.query(TABLE_USER, columns, null, null, null, null, sortOrder)
      //  val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val user = User(id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                    password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)))

                Log.d(DATABASE_NAME,
                    "ID= "+cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt()+"\n"+
                            "NAME ="+cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))+"\n"+
                            "EMAIL= "+cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL))+"\n"+
                            "PASSWORD="+cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)))
              userList.add(user)



            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    fun addUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)


     val rowId:Long=db.insert(TABLE_USER, null, values)
        Log.d(DATABASE_NAME,"ROW ID-"+rowId)
        db.close()
    }

    fun updateUser(user: User) {
    val db = this.writableDatabase

    val values = ContentValues()
    values.put(COLUMN_USER_NAME, user.name)
    values.put(COLUMN_USER_EMAIL, user.email)
    values.put(COLUMN_USER_PASSWORD, user.password)
    db.update(TABLE_USER, values, "$COLUMN_USER_ID = ?",
        arrayOf(user.id.toString()))
    db.close()
  }

    fun deleteUser(user: User) {

        val db = this.writableDatabase
        db.delete(TABLE_USER, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString()))
        db.close()


    }
// method vor@ stugum e email exist or not
    // miayn Email-@
fun checkUser(email: String): Boolean {


    val columns = arrayOf(COLUMN_USER_ID)
    val db = this.readableDatabase
    val selection = "$COLUMN_USER_EMAIL = ?"
    val selectionArgs = arrayOf(email)
    //SELECT user_id FROM user WHERE user_email = 'zveda.hayrapetyan.97@mail.ru';
    val cursor = db.query(TABLE_USER,
        columns,
        selection,
        selectionArgs,
        null,
        null,
        null)

    val cursorCount = cursor.count
    cursor.close()
    db.close()

    if (cursorCount > 0) {
        return true
    }

    return false
}
    //stugum e arden Exist e or not
    // ev Email ev Password
    fun checkUser(email: String, password: String): Boolean {

        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)
        /**
         * SELECT user_id FROM user WHERE user_email = 'zveda.hayrapetyan.97@mail.ru' AND user_password = 'zveda19';
         */
        val cursor = db.query(TABLE_USER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false

    }



    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "UserManager.db"
        private val TABLE_USER = "user"
        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_EMAIL = "user_email"
        private val COLUMN_USER_PASSWORD = "user_password"
    }

}
