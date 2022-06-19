package com.example.findfilms

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findfilms.databinding.FilmItemBinding
import kotlinx.android.synthetic.main.film_item.view.*

class FilmViewHolder(var binding:FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val title = itemView.title
    private val poster = itemView.poster
    private val description = itemView.description
    private val ratingDonut = itemView.rating_donut

    fun bind(film: Film) {
        title.text = film.title
        Glide.with(itemView)
            .load(film.poster)
            .into(poster)
        description.setText(film.short_desc)
        ratingDonut.setProgress((film.rating * 10).toInt())
    }
}