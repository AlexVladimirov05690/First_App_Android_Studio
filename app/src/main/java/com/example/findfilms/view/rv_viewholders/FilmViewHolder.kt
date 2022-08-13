package com.example.findfilms.view.rv_viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findfilms.com.example.findfilms.data.ApiConstants
import com.example.findfilms.databinding.FilmItemBinding
import com.example.findfilms.data.Entity.Film
import kotlinx.android.synthetic.main.film_item.view.*

class FilmViewHolder(var binding:FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val title = itemView.title
    private val poster = itemView.poster
    private val description = itemView.description
    private val ratingDonut = itemView.rating_donut

    fun bind(film: Film) {
        title.text = film.title
        Glide.with(itemView)
            .load(ApiConstants.IMAGES_URL + "w342" + film.poster)
            .centerInside()
            .into(poster)
        description.setText(film.description)
        ratingDonut.setProgress((film.rating * 10).toInt())
    }
}