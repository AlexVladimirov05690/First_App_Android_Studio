package com.example.findfilms.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findfilms.databinding.FragmentFavoritesBinding
import com.example.findfilms.domain.Film
import com.example.findfilms.utils.AnimationHelper
import com.example.findfilms.view.MainActivity
import com.example.findfilms.view.rv_adapetrs.FilmListRecyclerAdapter
import com.example.findfilms.view.rv_adapetrs.TopSpacingItemDecoration
import com.example.findfilms.viewmodel.FavoritesFragmentViewModel

class FavoritesFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(FavoritesFragmentViewModel ::class.java)
    }
    private lateinit var favoritesFilmAdapter: FilmListRecyclerAdapter
    private lateinit var binding: FragmentFavoritesBinding
    private var favoritesFilmDataBase = listOf<Film>()
        set(value) {
            if (field == value) return
            field = value
            favoritesFilmAdapter.addItems(field)
        }

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
        viewModel.favoritesFilmListLiveData.observe(viewLifecycleOwner) {
            favoritesFilmDataBase = it
            favoritesFilmAdapter.addItems(it)
        }
        initAdapter()

    }

    private fun initAdapter() {
        binding.favoritesRecycler.apply {
            favoritesFilmAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
            adapter = favoritesFilmAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)

        }
    }
}