package com.example.findfilms.domain

import com.example.findfilms.data.MainRepository

class Interactor(val repo: MainRepository) {
    fun getFilmsDB(): List<Film> = repo.filmDataBase
}