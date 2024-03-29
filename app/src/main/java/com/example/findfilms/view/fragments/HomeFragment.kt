package com.example.findfilms.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findfilms.view.rv_adapetrs.FilmListRecyclerAdapter
import com.example.findfilms.view.rv_adapetrs.TopSpacingItemDecoration
import java.util.*
import com.example.findfilms.databinding.FragmentHomeBinding
import com.example.findfilms.data.Entity.Film
import com.example.findfilms.utils.AnimationHelper
import com.example.findfilms.view.MainActivity
import com.example.findfilms.viewmodel.HomeFragmentViewModel

class HomeFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding
    private var filmDataBase = listOf<Film>()
        set(value) {
            if (field == value) return
            field = value
            filmsAdapter.addItems(field)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.homeFragmentRoot,
            requireActivity(),
            1
        )
        searchInit()
        viewModel.showProgressBar.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        viewModel.networkError.observe(viewLifecycleOwner) {
            Toast.makeText(view.context, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.filmsListLiveData.observe(viewLifecycleOwner) {
            filmDataBase = it
            filmsAdapter.addItems(it)
        }
        initAdapter()
        initPullToRefresh()


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
                if (p0.isEmpty()) {
                    filmsAdapter.addItems(filmDataBase)
                    return true
                }
                val result = filmDataBase.filter {
                    it.title.lowercase(Locale.getDefault())
                        .contains(p0.lowercase(Locale.getDefault()))
                }

                filmsAdapter.addItems(result)
                return true
            }

        })
    }


    private fun initAdapter() {
        binding.mainRecycler.apply {
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
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

    private fun initPullToRefresh(){
        binding.pullToRefresh.setOnRefreshListener {
            //Чистим адаптер(items нужно будет сделать паблик или создать для этого публичный метод)
            filmsAdapter.items.clear()
            //Делаем новый запрос фильмов на сервер
            viewModel.getFilms()
            //Убираем крутящееся колечко
            binding.pullToRefresh.isRefreshing = false
        }
    }
}