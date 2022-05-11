package com.example.findfilms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private val filmsDataBase = listOf(
        Film(1, "Достать ножи", R.drawable.knives_out, R.string.desc_knives_out, R.string.short_desc_knives_out),
        Film(2, "Убить Билла", R.drawable.kill_bill, R.string.desc_kill_bill, R.string.short_desc_kill_bill),
        Film(3, "Безумный Макс. Дорога ярости", R.drawable.mad_max, R.string.desc_mad_max, R.string.short_desc_mad_max),
        Film(4, "Матрица", R.drawable.the_matrix, R.string.desc_the_matrix, R.string.short_desc_the_matrix),
        Film(5, "Джанго освобожденный", R.drawable.django_unchained, R.string.desc_django_unchained, R.string.short_desc_django_unchained),
        Film(6, "По соображениям совести", R.drawable.hacksaw_ridge, R.string.desc_hacksaw_ridge, R.string.short_desc_hacksaw_ridge),
        Film(7, "Карты, Деньги, Два ствола", R.drawable.lock_stock_and_two_barrels, R.string.desc_lock_stock_and_two_barrels, R.string.short_desc_lock_stock_and_two_barrels),
        Film(8, "Большой куш", R.drawable.snatch, R.string.desc_snatch, R.string.short_desc_snatch),
        Film(9, "Отступники", R.drawable.the_departed, R.string.desc_departed, R.string.short_desc_departed),
        Film(10, "Зелёная миля", R.drawable.the_green_mile, R.string.desc_green_mile, R.string.short_desc_green_mile),
        Film(11, "Титаник", R.drawable.titanic, R.string.desc_titanic, R.string.short_desc_titanic)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        filmsAdapter.addItems(filmsAdapter, filmsDataBase)
    }


    private fun initAdapter() {
        main_recycler.apply {
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