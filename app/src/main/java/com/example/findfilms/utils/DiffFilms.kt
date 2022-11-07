package com.example.findfilms.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.findfilms.data.entity.Film

object DiffFilms {
    val CALLBACK: DiffUtil.ItemCallback<Film> = object : DiffUtil.ItemCallback<Film>(){
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.description == newItem.description
        }

    }
}