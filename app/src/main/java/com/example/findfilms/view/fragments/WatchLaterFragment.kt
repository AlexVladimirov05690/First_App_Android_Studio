package com.example.findfilms.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findfilms.data.Entity.Film
import com.example.findfilms.databinding.FragmentWatchLaterBinding
import com.example.findfilms.utils.AnimationHelper
import com.example.findfilms.view.MainActivity
import com.example.findfilms.view.rv_adapetrs.FilmListRecyclerAdapter
import com.example.findfilms.view.rv_adapetrs.TopSpacingItemDecoration
import com.example.findfilms.viewmodel.WatchLaterFragmentViewModel

class WatchLaterFragment: Fragment() {
    private lateinit var binding: FragmentWatchLaterBinding
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(WatchLaterFragmentViewModel::class.java)
    }
    private lateinit var filmsWatchLaterAdapter: FilmListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchLaterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(binding.watchLaterFragmentRoot, requireActivity(), 3)
        initAdapter()
    }

    private fun initAdapter() {
        binding.mainRecycler.apply {
            filmsWatchLaterAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
            adapter = filmsWatchLaterAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)

        }
    }
}