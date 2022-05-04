package com.example.findfilms

import androidx.recyclerview.widget.DiffUtil

class FilmDiff(private val oldList: List<Film>, private val newList: List<Film>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.title == new.title &&
                old.short_desc == new.short_desc &&
                old.description == new.description &&
                old.poster == new.poster
    }
}