package com.example.findfilms

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 1) {
            AlertDialog.Builder(this)
                .setTitle(R.string.do_you_want_exit)
                .setIcon(R.drawable.ic_launcher_foreground)
                .setPositiveButton(R.string.yes) {_,_ ->
                    finish()
                }
                .setNegativeButton(R.string.no) {_,_ ->
                }
                .show()
        }
        else {
            super.onBackPressed()
        }
    }

    private fun initButton() {

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.set -> {
                    Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        Bottom_Menu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.main -> {
                    val tag = "main"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment?: HomeFragment(), tag)
                    true
                }
                R.id.fav -> {
                    val tag = "favorites"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment?: FavoritesFragment(), tag)
                    true
                }
                R.id.watch_later_menu -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment?: WatchLaterFragment(), tag)
                    true
                }
                R.id.selections -> {
                    val tag = "selections"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment?: SelectionsFragment(), tag)
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

    private fun checkFragmentExistence(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_place, fragment, tag)
            .addToBackStack(null)
            .commit()
    }
}