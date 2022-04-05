package com.example.findfilms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initButtons()
    }

    private fun initButtons() {

        topAppBar.setOnMenuItemClickListener() {
            when (it.itemId) {
                R.id.set -> {
                    Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        Bottom_Menu.setOnItemSelectedListener() {
            when (it.itemId) {
                R.id.fav -> {
                    Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.rec -> {
                    Toast.makeText(this, "Рекомендации", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.in_wishlist -> {
                    Toast.makeText(this, "В список желаемого", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        find_film.setOnClickListener {
            Toast.makeText(this, "Найти фильм", Toast.LENGTH_SHORT).show()
        }
        favorites.setOnClickListener {
            Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show()
        }
        recommendations.setOnClickListener {
            Toast.makeText(this, "Рекомендации", Toast.LENGTH_SHORT).show()
        }
        wishlist.setOnClickListener {
            Toast.makeText(this, "В список желаний", Toast.LENGTH_SHORT).show()
        }
        setting.setOnClickListener {
            Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
        }
    }
}