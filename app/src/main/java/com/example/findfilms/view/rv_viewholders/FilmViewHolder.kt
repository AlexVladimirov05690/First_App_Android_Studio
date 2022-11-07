package com.example.findfilms.view.rv_viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findfilms.com.example.findfilms.data.ApiConstants
import com.example.findfilms.databinding.FilmItemBinding
import com.example.findfilms.data.entity.Film

class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val filmItemBinding = FilmItemBinding.bind(itemView)
    private val title = filmItemBinding.title
    private val poster = filmItemBinding.poster
    private val description = filmItemBinding.description
    private val ratingDonut = filmItemBinding.ratingDonut

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