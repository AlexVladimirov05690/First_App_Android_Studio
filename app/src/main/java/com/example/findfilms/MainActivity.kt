package com.example.findfilms

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initButton()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_place, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun initButton() {

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
    }

    fun launchDetailsFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable("film", film)
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_place, fragment)
            .addToBackStack(null)
            .commit()
    }
}