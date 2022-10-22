package com.example.findfilms.view.rv_adapetrs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.findfilms.view.rv_viewholders.FilmViewHolder
import com.example.findfilms.R
import com.example.findfilms.databinding.FilmItemBinding
import com.example.findfilms.data.Entity.Film
import com.example.findfilms.utils.DiffFilms


class FilmListRecyclerAdapter(private val clickListener: OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = mutableListOf<Film>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilmViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilmViewHolder -> {
                holder.bind(items[position])
                holder.itemView.setOnClickListener{
                    clickListener.click(items[position])
                }
            }
        }
    }


    fun addItems(list: List<Film>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun addItem(film: Film) {
        items.add(film)
    }

    fun removeItem(film: Film) {
        items.remove(film)
    }



    interface OnItemClickListener {
        fun click(film: Film)
    }



}