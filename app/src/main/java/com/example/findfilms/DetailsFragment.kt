package com.example.findfilms


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val film = arguments?.get("film") as Film
        details_toolbar.title = film.title
        details_poster.setImageResource(film.poster)
        details_description.setText(film.description)
        initButtons()
    }

    private fun initButtons() {
        watch_later.setOnClickListener {
            Snackbar.make(main_activity, "Смотреть позже", Snackbar.LENGTH_SHORT).show()
        }
        details_in_wish.setOnClickListener {
            Snackbar.make(main_activity, "В избранное", Snackbar.LENGTH_SHORT).show()
        }
        details_fab.setOnClickListener {
            Snackbar.make(main_activity, "Поделиться", Snackbar.LENGTH_SHORT).show()
        }
    }


}