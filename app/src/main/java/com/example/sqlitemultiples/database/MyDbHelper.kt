package com.example.sqlitemultiples.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.VERSION
import com.example.sqlitemultiples.models.Category
import com.example.sqlitemultiples.models.Film
import com.example.sqlitemultiples.myinterface.MyPlan

const val VERSION = 1
const val NAME = "offline.db"


const val TABLE_NAME = "category"
const val CATEGORY_ID = "id"
const val CATEGORY_NAME = "name"


const val TABLE_FILM = "film"
const val FILM_ID = "id"
const val FILM_NAME = "name"
const val FILM_DATE = "year"
const val FILM_TYPE = "category"

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION), MyPlan {
    override fun onCreate(p0: SQLiteDatabase?) {

        val queryCategory =
            "create table $TABLE_NAME($CATEGORY_ID integer not null primary key autoincrement unique,$CATEGORY_NAME text not null)"
        val queryFilm =
            "create table $TABLE_FILM($FILM_ID integer not null primary key autoincrement unique,$FILM_NAME text not null,$FILM_DATE text not null,$FILM_TYPE integer not null ,foreign key($FILM_TYPE)references $TABLE_NAME ($CATEGORY_ID))"
        p0?.execSQL(queryCategory)
        p0?.execSQL(queryFilm)
    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    override fun addFilm(film: Film) {
        val writer = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(FILM_ID, film.id)
        contentValues.put(FILM_NAME, film.name)
        contentValues.put(FILM_DATE, film.yili)
        contentValues.put(FILM_TYPE, film.category?.id)
        writer.insert(TABLE_FILM, null, contentValues)
        writer.close()

    }

    override fun addCategory(category: Category) {
        val writer = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CATEGORY_ID, category.id)
        contentValues.put(CATEGORY_NAME, category.name)
        writer.insert(TABLE_NAME, null, contentValues)
        writer.close()


    }


    override fun getFilms(): ArrayList<Film> {
        val reader = this.readableDatabase
        val list = ArrayList<Film>()
        val query = "select  * from $TABLE_FILM"
        val cursor = reader.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val film = Film(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    getCatergoryByid(cursor.getInt(3))
                )
                list.add(film)
            } while (cursor.moveToNext())
        }
        return list
    }

    private fun getCatergoryByid(id: Int): Category? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME, arrayOf(CATEGORY_ID, CATEGORY_NAME), "$CATEGORY_ID =?",
            arrayOf(id.toString()), null, null, null
        )

        cursor.moveToFirst()
        val category = Category(cursor.getInt(0), cursor.getString(1))
        return category
    }

    override fun getCategories(): ArrayList<Category> {
        val reader = this.readableDatabase
        val list = ArrayList<Category>()
        val query = "select * from $TABLE_NAME"
        val cursor = reader.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val category = Category(cursor.getInt(0), cursor.getString(1))

                list.add(category)
            } while (cursor.moveToNext())
        }
        return list

    }

    override fun editFilm(film: Film) {
        val editor = this.writableDatabase
        val contentValus = ContentValues()
        contentValus.put(FILM_ID, film.id)
        contentValus.put(FILM_NAME, film.name)
        contentValus.put(FILM_DATE, film.yili)
        contentValus.put(FILM_TYPE, film.category?.id)
        editor.update(TABLE_FILM, contentValus, "$FILM_ID =?", arrayOf(film))

    }

    override fun deleteFilm(film: Film) {
        val query = "select * from $TABLE_FILM where $FILM_ID =${film.id}"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val film = Film(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    getCatergoryByid(cursor.getInt(3))
                )
            } while (cursor.moveToNext())
        }


    }

    override fun deleteCategory(category: Category) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$CATEGORY_ID =?", arrayOf(category.id.toString()))


    }


}