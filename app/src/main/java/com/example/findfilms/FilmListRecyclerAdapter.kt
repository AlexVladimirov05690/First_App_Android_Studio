package com.example.findfilms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.film_item.view.*


class FilmListRecyclerAdapter(private val clickListener: OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Film>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilmViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilmViewHolder -> {
                holder.bind(items[position])
                holder.itemView.item_container.setOnClickListener{
                    clickListener.click(items[position])
                }
            }
        }
    }


    fun addItems(adapter: FilmListRecyclerAdapter, newList: List<Film>) {
        val oldList = adapter.items
        val filmDiff = FilmDiff(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(filmDiff)
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(adapter)
    }

    interface OnItemClickListener {
        fun click(film: Film)
    }



}