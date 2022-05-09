package com.example.findfilms

class FavoriteFilms {

    fun favoriteList(filmsDataBase: List<Film>): List<Film>{
        var resultList: List<Film> = emptyList()
        for (i in 0..filmsDataBase.size){
            if(filmsDataBase[i].isInFavorites) {
                resultList.plus(filmsDataBase[i])
            }
        }
        return resultList.ifEmpty {
            emptyList()
        }
    }
}