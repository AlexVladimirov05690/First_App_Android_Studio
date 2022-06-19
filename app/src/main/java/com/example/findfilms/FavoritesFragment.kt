package com.example.findfilms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findfilms.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private lateinit var favFilmsAdapter: FilmListRecyclerAdapter
    private lateinit var binding: FragmentFavoritesBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.root,
            requireActivity(),
            2
        )
        initAdapter()
        favFilmsAdapter.addItems(FilmsBase.getFavoriteFilms())

    }

    private fun initAdapter() {
        binding.favoritesRecycler.apply {
            favFilmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
            adapter = favFilmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)

        }

    }
}