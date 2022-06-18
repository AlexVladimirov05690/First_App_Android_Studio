package com.example.findfilms


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {
    private lateinit var film: Film
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        film = arguments?.get("film") as Film
        details_toolbar.title = film.title
        details_poster.setImageResource(film.poster)
        details_description.setText(film.description)
        initButtons()
        details_in_wish.setImageResource(
            if (film.isInFavorites) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24
        )
    }

    private fun initButtons() {
        watch_later.setOnClickListener {
            Snackbar.make(details, "Смотреть позже", Snackbar.LENGTH_SHORT).show()
        }
        details_in_wish.setOnClickListener {
            if(!film.isInFavorites) {
                details_in_wish.setImageResource(R.drawable.ic_baseline_favorite_24)
                film.isInFavorites = true
                Snackbar.make(details, "Фильм добавлен в Избранное", Snackbar.LENGTH_SHORT).show()
            } else {
                details_in_wish.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                film.isInFavorites = false
                Snackbar.make(details, "Фильм удалён из Избранного", Snackbar.LENGTH_SHORT).show()
            }
        }
        details_fab.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this film: ${film.title} \n\n ${film.description}"
            )
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }


}