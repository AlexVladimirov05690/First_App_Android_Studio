package com.example.findfilms.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.findfilms.data.Entity.Film

class DiffFilms(private val oldListFilms: List<Film>, private val newListFilms: List<Film>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldListFilms.size
    }

    override fun getNewListSize(): Int {
        return newListFilms.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListFilms[oldItemPosition].id == newListFilms[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFilm = oldListFilms[oldItemPosition]
        val newFilm = newListFilms[newItemPosition]
        return oldFilm.title == newFilm.title &&
                oldFilm.description == newFilm.description
    }

}