package com.example.findfilms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.*
import com.example.findfilms.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(binding.homeFragmentRoot, requireActivity(), 1)
        initAdapter()
        filmsAdapter.addItems(FilmsBase.getFilms())
        searchInit()
    }

    private fun searchInit() {
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(p0: String): Boolean {
                if(p0.isEmpty()) {
                    filmsAdapter.addItems(FilmsBase.getFilms())
                    return true
                }
                val result = FilmsBase.getFilms().filter {
                    it.title.lowercase(Locale.getDefault()).contains(p0.lowercase(Locale.getDefault()))
                }

                filmsAdapter.addItems(result)
                return true
            }

        })
    }


    private fun initAdapter() {
        binding.mainRecycler.apply {
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
                override fun click(film: Film) {
                    (requireActivity() as MainActivity).launchDetailsFragment(film)
                }
            })
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)

        }
    }
}