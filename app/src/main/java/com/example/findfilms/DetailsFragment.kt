package com.example.findfilms


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.findfilms.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var film: Film
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        film = arguments?.get("film") as Film
        binding.detailsToolbar.title = film.title
        binding.detailsPoster.setImageResource(film.poster)
        binding.detailsDescription.setText(film.description)
        initButtons()
        binding.detailsInWish.setImageResource(
            if (film.isInFavorites) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24
        )
    }

    private fun initButtons() {
        binding.watchLater.setOnClickListener {
            Snackbar.make(binding.root, R.string.watch_later_menu, Snackbar.LENGTH_SHORT).show()
        }
        binding.detailsInWish.setOnClickListener {
            if(!film.isInFavorites) {
                binding.detailsInWish.setImageResource(R.drawable.ic_baseline_favorite_24)
                film.isInFavorites = true
                Snackbar.make(binding.root, R.string.film_add_to_favorite, Snackbar.LENGTH_SHORT).show()
            } else {
                binding.detailsInWish.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                film.isInFavorites = false
                Snackbar.make(binding.root, R.string.film_remove_from_favorite, Snackbar.LENGTH_SHORT).show()
            }
        }
       binding.detailsFab.setOnClickListener {
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