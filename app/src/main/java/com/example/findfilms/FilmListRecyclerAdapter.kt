package com.example.findfilms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.findfilms.databinding.FilmItemBinding


class FilmListRecyclerAdapter(private val clickListener: OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = mutableListOf<Film>()
    private lateinit var binding: FilmItemBinding

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = FilmItemBinding.inflate(inflater, parent, false)
        return FilmViewHolder(DataBindingUtil.inflate(inflater, R.layout.film_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilmViewHolder -> {
                holder.bind(items[position])
                holder.binding.itemContainer.setOnClickListener{
                    clickListener.click(items[position])
                    binding.executePendingBindings()
                }
            }
        }
    }


    fun addItems(list: List<Film>) {
        items.clear()
        items.addAll(list)
    }


    interface OnItemClickListener {
        fun click(film: Film)
    }



}