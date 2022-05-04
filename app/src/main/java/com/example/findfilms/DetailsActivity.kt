package com.example.findfilms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val film = intent.extras?.get("film") as Film
        details_toolbar.title = film.title
        details_poster.setImageResource(film.poster)
        details_description.setText(film.description)
        initButton()
    }

    private fun initButton() {
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