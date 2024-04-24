package com.example.sqlitemultiples.myinterface

import com.example.sqlitemultiples.models.Category
import com.example.sqlitemultiples.models.Film

interface MyPlan {
    abstract val getCatergoryid: Category?

    fun addFilm(film: Film)
    fun addCategory(category: Category)
    fun getFilms():ArrayList<Film>
    fun getCategories():ArrayList<Category>
    fun editFilm(film: Film)
    fun deleteFilm(film: Film)
    fun deleteCategory(category: Category)
}