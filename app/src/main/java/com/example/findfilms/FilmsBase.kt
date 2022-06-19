package com.example.findfilms

object FilmsBase {
    private var filmsDataBaseFromClass = mutableListOf(
        Film(
            1,
            "Достать ножи",
            R.drawable.knives_out,
            R.string.desc_knives_out,
            R.string.short_desc_knives_out, 7.7f
        ),
        Film(
            2,
            "Убить Билла",
            R.drawable.kill_bill,
            R.string.desc_kill_bill,
            R.string.short_desc_kill_bill, 6.3f
        ),
        Film(
            3,
            "Безумный Макс. Дорога ярости",
            R.drawable.mad_max,
            R.string.desc_mad_max,
            R.string.short_desc_mad_max, 4.3f
        ),
        Film(
            4,
            "Матрица",
            R.drawable.the_matrix,
            R.string.desc_the_matrix,
            R.string.short_desc_the_matrix, 8.9f
        ),
        Film(
            5,
            "Джанго освобожденный",
            R.drawable.django_unchained,
            R.string.desc_django_unchained,
            R.string.short_desc_django_unchained, 7.9f
        ),
        Film(
            6,
            "По соображениям совести",
            R.drawable.hacksaw_ridge,
            R.string.desc_hacksaw_ridge,
            R.string.short_desc_hacksaw_ridge, 7.6f
        ),
        Film(
            7,
            "Карты, Деньги, Два ствола",
            R.drawable.lock_stock_and_two_barrels,
            R.string.desc_lock_stock_and_two_barrels,
            R.string.short_desc_lock_stock_and_two_barrels, 8.7f
        ),
        Film(8, "Большой куш", R.drawable.snatch, R.string.desc_snatch, R.string.short_desc_snatch, 9.8f),
        Film(
            9,
            "Отступники",
            R.drawable.the_departed,
            R.string.desc_departed,
            R.string.short_desc_departed, 8.3f
        ),
        Film(
            10,
            "Зелёная миля",
            R.drawable.the_green_mile,
            R.string.desc_green_mile,
            R.string.short_desc_green_mile, 9.1f
        ),
        Film(11, "Титаник", R.drawable.titanic, R.string.desc_titanic, R.string.short_desc_titanic, 7.8f)
    )

    fun getFilms(): List<Film> {
        return filmsDataBaseFromClass
    }

    fun getFavoriteFilms(): List<Film> {
        var resultList = mutableListOf<Film>()
        for (i in filmsDataBaseFromClass.indices) {
            if (filmsDataBaseFromClass[i].isInFavorites) {
                resultList.add(filmsDataBaseFromClass[i])
            }
        }
        return resultList

    }
}